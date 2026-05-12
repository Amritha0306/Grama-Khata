package com.example.gramakhata.ui.screens.customer;

import androidx.lifecycle.SavedStateHandle;
import com.example.gramakhata.data.repository.LedgerRepository;
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
public final class CustomerViewModel_Factory implements Factory<CustomerViewModel> {
  private final Provider<LedgerRepository> repositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public CustomerViewModel_Factory(Provider<LedgerRepository> repositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.repositoryProvider = repositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public CustomerViewModel get() {
    return newInstance(repositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static CustomerViewModel_Factory create(Provider<LedgerRepository> repositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new CustomerViewModel_Factory(repositoryProvider, savedStateHandleProvider);
  }

  public static CustomerViewModel newInstance(LedgerRepository repository,
      SavedStateHandle savedStateHandle) {
    return new CustomerViewModel(repository, savedStateHandle);
  }
}
