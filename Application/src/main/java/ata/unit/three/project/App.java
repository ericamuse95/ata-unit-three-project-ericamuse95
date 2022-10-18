package ata.unit.three.project;

import ata.unit.three.project.expense.dynamodb.ExpenseItem;
import ata.unit.three.project.expense.dynamodb.ExpenseServiceRepository;
import ata.unit.three.project.expense.service.ExpenseService;
import ata.unit.three.project.expense.service.ExpenseServiceComponent;
import ata.unit.three.project.expense.service.model.ExpenseItemConverter;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;

@Module
public class App {
//    @Provides
//    public static ExpenseService expenseService() {
//        return new ExpenseService(new ExpenseServiceRepository(), new ExpenseItemConverter());
//    }

    @Provides
    public ExpenseItemConverter providesExpenseItemConverter(){
        return new ExpenseItemConverter();
    }

    @Provides
    public ExpenseServiceRepository providesExpenseServiceRepository() {
        return new ExpenseServiceRepository();
    }

}

//expenseService should be removed
