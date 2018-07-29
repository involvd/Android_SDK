package com.involvd.sdk.data;

import android.arch.persistence.room.Room
import android.content.Context
import com.involvd.BuildConfig
import com.involvd.sdk.data.models.BugReport
import com.involvd.sdk.data.models.FeatureRequest
import com.involvd.sdk.data.models.Status
import com.involvd.sdk.data.room.AppDatabase
import com.involvd.sdk.data.viewmodels.BugReportViewModel
import com.involvd.sdk.data.viewmodels.FeatureRequestViewModel
import com.robj.radicallyreusable.base.components.Optional
import io.reactivex.*
import io.reactivex.schedulers.Schedulers
import kotlin.reflect.KFunction1


/**
 * Created by jj on 14/01/18.
 */

class DatabaseManager {

    companion object {

        private val TAG = DatabaseManager::class.java.simpleName
        private var database: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if(database == null) {
                val builder = Room.databaseBuilder(context, AppDatabase::class.java, BuildConfig.APPLICATION_ID)
                if (BuildConfig.DEBUG && BuildConfig.VERSION_CODE == 1)
                    builder.fallbackToDestructiveMigration()
                database = builder.build()
            }
            return database!!
        }
        
        /** Bug Report **/
        @JvmStatic
        fun addOrUpdateBugReport(context: Context, bugReport: BugReport): Observable<Boolean> {
            return performDbAction(bugReport, getDatabase(context).bugReportDao()::insertOrUpdate)
        }

        @JvmStatic
        fun addOrUpdateBugReports(context: Context, bugReports: List<BugReport>): Observable<Boolean> {
            return performDbListAction(bugReports, getDatabase(context).bugReportDao()::insertOrUpdateAll)
        }

        @JvmStatic
        fun getBugReports(context: Context): Flowable<MutableList<BugReport>> {
            return getDatabase(context).bugReportDao().getBugReports().compose(applyFlowableRules())
        }

        @JvmStatic
        fun getBugReportsOfStatus(context: Context, appId: String, status: Status): Flowable<MutableList<BugReportViewModel>>? {
            return getDatabase(context).bugReportDao().getBugReportsOfStatus(appId, Converters.reportStatusToString(status)).compose(applyFlowableRules())
        }

        @JvmStatic
        fun getBugReport(context: Context, bugReportId: String): Observable<Optional<BugReportViewModel>> {
            return performGetAction(bugReportId, getDatabase(context).bugReportDao()::getBugReport)
        }

        /** Bug Report **/
        @JvmStatic
        fun addOrUpdateFeatureRequest(context: Context, featureRequest: FeatureRequest): Observable<Boolean> {
            return performDbAction(featureRequest, getDatabase(context).featureRequestDao()::insertOrUpdate)
        }

        @JvmStatic
        fun addOrUpdateFeatureRequests(context: Context, featureRequests: List<FeatureRequest>): Observable<Boolean> {
            return performDbListAction(featureRequests, getDatabase(context).featureRequestDao()::insertOrUpdateAll)
        }

        @JvmStatic
        fun getFeatureRequests(context: Context): Flowable<MutableList<FeatureRequest>> {
            return getDatabase(context).featureRequestDao().featureRequests.compose(applyFlowableRules())
        }

        @JvmStatic
        fun getFeatureRequestsOfStatus(context: Context, appId: String, status: Status): Flowable<MutableList<FeatureRequestViewModel>>? {
            return getDatabase(context).featureRequestDao().getFeatureRequestsOfStatus(appId, Converters.reportStatusToString(status)).compose(applyFlowableRules())
        }

        @JvmStatic
        fun getFeatureRequest(context: Context, featureRequestId: String): Observable<Optional<FeatureRequestViewModel>> {
            return performGetAction(featureRequestId, getDatabase(context).featureRequestDao()::getFeatureRequest)
        }

            private fun <T, R> performGetAction(data: T, dbMethod: KFunction1<T, R>): Observable<Optional<R>> {
            return Observable.create({ e: ObservableEmitter<Optional<R>> ->
                val result = dbMethod(data)
                if (!e.isDisposed) {
                    try {
                        e.onNext(Optional(result))
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                        e.onError(ex)
                    }
                }
            })
                    .compose(applyObservableRules())
        }

        private fun <T, R> performGetAction(data: List<T>, dbMethod: KFunction1<List<T>, R>): Observable<R> {
            return Observable.create({ e: ObservableEmitter<R> ->
                val result = dbMethod(data)
                if (!e.isDisposed) {
                    try {
                        e.onNext(result)
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                        e.onError(ex)
                    }
                }
            })
                    .compose(applyObservableRules())
        }

        private fun <T> performDbAction(data: T, dbMethod: KFunction1<T, Any>): Observable<Boolean> {
            return Observable.create({ e: ObservableEmitter<Boolean> ->
                val rowsAffected = dbMethod(data)
                doOnActionComplete(rowsAffected, e)
            })
            .compose(applyObservableRules())
        }

        private fun <T> performDbListAction(data: List<T>, dbMethod: KFunction1<List<T>, Any>): Observable<Boolean> {
            return Observable.create({ e: ObservableEmitter<Boolean> ->
                val rowsAffected = dbMethod(data)
                doOnActionComplete(rowsAffected, e);
            })
            .compose(applyObservableRules())
        }

        private fun doOnActionComplete(rowsAffected: Any, e: ObservableEmitter<Boolean>) {
            if (!e.isDisposed) {
                try {
                    when (rowsAffected) {
                        is Long -> e.onNext(rowsAffected > -1)
                        is LongArray -> e.onNext(rowsAffected.size > -1)
                        is Int -> e.onNext(rowsAffected > -1)
                        else -> e.onNext(true)
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    e.onError(ex)
                }
            }
        }

        fun <T> applyObservableRules(): ObservableTransformer<T, T> {
            return ObservableTransformer { observable ->
                observable
                        .take(1)
                        .subscribeOn(Schedulers.computation());
            }
        }

        fun <T> applyFlowableRules(): FlowableTransformer<T, T> {
            return FlowableTransformer { observable ->
                observable
                        .take(1)
                        .subscribeOn(Schedulers.computation());
            }
        }

    }

}
