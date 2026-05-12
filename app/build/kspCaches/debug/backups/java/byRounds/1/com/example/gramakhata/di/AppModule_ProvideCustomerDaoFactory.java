package com.example.gramakhata.di;

import com.example.gramakhata.data.local.AppDatabase;
import com.example.gramakhata.data.local.dao.CustomerDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class AppModule_ProvideCustomerDaoFactory implements Factory<CustomerDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideCustomerDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public CustomerDao get() {
    return provideCustomerDao(dbProvider.get());
  }

  public static AppModule_ProvideCustomerDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideCustomerDaoFactory(dbProvider);
  }

  public static CustomerDao provideCustomerDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideCustomerDao(db));
  }
}
