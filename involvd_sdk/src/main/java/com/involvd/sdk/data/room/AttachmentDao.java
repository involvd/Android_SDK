package com.involvd.sdk.data.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.involvd.sdk.data.models.Attachment;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by jj on 27/06/17.
 */

@Dao
public interface AttachmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertOrUpdate(Attachment attachment); //long being the row number

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long[] insertOrUpdateAll(List<Attachment> attachments);

    @Delete
    public abstract int delete(Attachment attachment);

    @Delete
    public abstract int delete(List<Attachment> attachments);

    @Query("SELECT * FROM attachment")
    public abstract Flowable<List<Attachment>> getAll();

    @Query("SELECT * FROM attachment WHERE " + Attachment.FIELD_ID + " in (:attachmentIds)")
    public abstract Flowable<List<Attachment>> getAttachments(List<String> attachmentIds);

    @Query("SELECT * FROM attachment WHERE " + Attachment.FIELD_ID + " = :attachmentId LIMIT 1")
    public abstract Attachment getAttachment(String attachmentId);

}
