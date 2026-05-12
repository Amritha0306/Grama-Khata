package com.example.gramakhata.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.gramakhata.data.local.entity.Shopkeeper;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ShopkeeperDao_Impl implements ShopkeeperDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Shopkeeper> __insertionAdapterOfShopkeeper;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public ShopkeeperDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfShopkeeper = new EntityInsertionAdapter<Shopkeeper>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `shopkeeper` (`id`,`name`,`shopName`,`phoneNumber`,`shopImageUri`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Shopkeeper entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getShopName());
        statement.bindString(4, entity.getPhoneNumber());
        if (entity.getShopImageUri() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getShopImageUri());
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM shopkeeper";
        return _query;
      }
    };
  }

  @Override
  public Object insertShopkeeper(final Shopkeeper shopkeeper,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfShopkeeper.insertAndReturnId(shopkeeper);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getShopkeeperByPhone(final String phoneNumber,
      final Continuation<? super Shopkeeper> $completion) {
    final String _sql = "SELECT * FROM shopkeeper WHERE phoneNumber = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, phoneNumber);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Shopkeeper>() {
      @Override
      @Nullable
      public Shopkeeper call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfShopName = CursorUtil.getColumnIndexOrThrow(_cursor, "shopName");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfShopImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "shopImageUri");
          final Shopkeeper _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpShopName;
            _tmpShopName = _cursor.getString(_cursorIndexOfShopName);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpShopImageUri;
            if (_cursor.isNull(_cursorIndexOfShopImageUri)) {
              _tmpShopImageUri = null;
            } else {
              _tmpShopImageUri = _cursor.getString(_cursorIndexOfShopImageUri);
            }
            _result = new Shopkeeper(_tmpId,_tmpName,_tmpShopName,_tmpPhoneNumber,_tmpShopImageUri);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<Shopkeeper> getFirstShopkeeper() {
    final String _sql = "SELECT * FROM shopkeeper LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"shopkeeper"}, new Callable<Shopkeeper>() {
      @Override
      @Nullable
      public Shopkeeper call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfShopName = CursorUtil.getColumnIndexOrThrow(_cursor, "shopName");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfShopImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "shopImageUri");
          final Shopkeeper _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpShopName;
            _tmpShopName = _cursor.getString(_cursorIndexOfShopName);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpShopImageUri;
            if (_cursor.isNull(_cursorIndexOfShopImageUri)) {
              _tmpShopImageUri = null;
            } else {
              _tmpShopImageUri = _cursor.getString(_cursorIndexOfShopImageUri);
            }
            _result = new Shopkeeper(_tmpId,_tmpName,_tmpShopName,_tmpPhoneNumber,_tmpShopImageUri);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
