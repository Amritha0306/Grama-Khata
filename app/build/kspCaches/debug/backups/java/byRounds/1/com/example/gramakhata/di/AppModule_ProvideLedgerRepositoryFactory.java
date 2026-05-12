package com.example.gramakhata.di;

import com.example.gramakhata.data.local.dao.CustomerDao;
import com.example.gramakhata.data.local.dao.ShopkeeperDao;
import com.example.gramakhata.data.local.dao.TransactionDao;
import com.example.gramakhata.data.repository.LedgerRepository;
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
public final class AppModule_ProvideLedgerRepositoryFactory implements Factory<LedgerRepository> {
  private final Provider<CustomerDao> customerDaoProvider;

  private final Provider<TransactionDao> transactionDaoProvider;

  private final Provider<ShopkeeperDao> shopkeeperDaoProvider;

  public AppModule_ProvideLedgerRepositoryFactory(Provider<CustomerDao> customerDaoProvider,
      Provider<TransactionDao> transactionDaoProvider,
      Provider<ShopkeeperDao> shopkeeperDaoProvider) {
    this.customerDaoProvider = customerDaoProvider;
    this.transactionDaoProvider = transactionDaoProvider;
    this.shopkeeperDaoProvider = shopkeeperDaoProvider;
  }

  @Override
  public LedgerRepository get() {
    return provideLedgerRepository(customerDaoProvider.get(), transactionDaoProvider.get(), shopkeeperDaoProvider.get());
  }

  public static AppModule_ProvideLedgerRepositoryFactory create(
      Provider<CustomerDao> customerDaoProvider, Provider<TransactionDao> transactionDaoProvider,
      Provider<ShopkeeperDao> shopkeeperDaoProvider) {
    return new AppModule_ProvideLedgerRepositoryFactory(customerDaoProvider, transactionDaoProvider, shopkeeperDaoProvider);
  }

  public static LedgerRepository provideLedgerRepository(CustomerDao customerDao,
      TransactionDao transactionDao, ShopkeeperDao shopkeeperDao) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideLedgerRepository(customerDao, transactionDao, shopkeeperDao));
  }
}
