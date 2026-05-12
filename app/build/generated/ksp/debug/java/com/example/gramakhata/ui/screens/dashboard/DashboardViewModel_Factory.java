package com.example.gramakhata.ui.screens.dashboard;

import com.example.gramakhata.data.local.SessionManager;
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<LedgerRepository> repositoryProvider;

  private final Provider<SessionManager> sessionManagerProvider;

  public DashboardViewModel_Factory(Provider<LedgerRepository> repositoryProvider,
      Provider<SessionManager> sessionManagerProvider) {
    this.repositoryProvider = repositoryProvider;
    this.sessionManagerProvider = sessionManagerProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(repositoryProvider.get(), sessionManagerProvider.get());
  }

  public static DashboardViewModel_Factory create(Provider<LedgerRepository> repositoryProvider,
      Provider<SessionManager> sessionManagerProvider) {
    return new DashboardViewModel_Factory(repositoryProvider, sessionManagerProvider);
  }

  public static DashboardViewModel newInstance(LedgerRepository repository,
      SessionManager sessionManager) {
    return new DashboardViewModel(repository, sessionManager);
  }
}
