package ata.unit.three.project.expense.service;

import ata.unit.three.project.App;
import dagger.Component;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Singleton;

//pass the App.class as modules
@Singleton
@Component(modules = {App.class})
public interface ExpenseServiceComponent {
    ExpenseService expenseService();
}


//ExpenseServiceComponent must have expenseService property
//expenseService must exist in ExpenseServiceComponent


