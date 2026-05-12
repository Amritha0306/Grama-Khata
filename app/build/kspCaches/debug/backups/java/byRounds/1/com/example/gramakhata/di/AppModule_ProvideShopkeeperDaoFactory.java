package com.example.gramakhata.di;

import com.example.gramakhata.data.local.AppDatabase;
import com.example.gramakhata.data.local.dao.ShopkeeperDao;
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
public final class AppModule_ProvideShopkeeperDaoFactory implements Factory<ShopkeeperDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideShopkeeperDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public ShopkeeperDao get() {
    return provideShopkeeperDao(dbProvider.get());
  }

  public static AppModule_ProvideShopkeeperDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideShopkeeperDaoFactory(dbProvider);
  }

  public static ShopkeeperDao provideShopkeeperDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideShopkeeperDao(db));
  }
}
