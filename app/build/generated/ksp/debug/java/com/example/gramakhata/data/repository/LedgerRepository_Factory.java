package com.example.gramakhata.data.repository;

import com.example.gramakhata.data.local.dao.CustomerDao;
import com.example.gramakhata.data.local.dao.ShopkeeperDao;
import com.example.gramakhata.data.local.dao.TransactionDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class LedgerRepository_Factory implements Factory<LedgerRepository> {
  private final Provider<CustomerDao> customerDaoProvider;

  private final Provider<TransactionDao> transactionDaoProvider;

  private final Provider<ShopkeeperDao> shopkeeperDaoProvider;

  public LedgerRepository_Factory(Provider<CustomerDao> customerDaoProvider,
      Provider<TransactionDao> transactionDaoProvider,
      Provider<ShopkeeperDao> shopkeeperDaoProvider) {
    this.customerDaoProvider = customerDaoProvider;
    this.transactionDaoProvider = transactionDaoProvider;
    this.shopkeeperDaoProvider = shopkeeperDaoProvider;
  }

  @Override
  public LedgerRepository get() {
    return newInstance(customerDaoProvider.get(), transactionDaoProvider.get(), shopkeeperDaoProvider.get());
  }

  public static LedgerRepository_Factory create(Provider<CustomerDao> customerDaoProvider,
      Provider<TransactionDao> transactionDaoProvider,
      Provider<ShopkeeperDao> shopkeeperDaoProvider) {
    return new LedgerRepository_Factory(customerDaoProvider, transactionDaoProvider, shopkeeperDaoProvider);
  }

  public static LedgerRepository newInstance(CustomerDao customerDao, TransactionDao transactionDao,
      ShopkeeperDao shopkeeperDao) {
    return new LedgerRepository(customerDao, transactionDao, shopkeeperDao);
  }
}
