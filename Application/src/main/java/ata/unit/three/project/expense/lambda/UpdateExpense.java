package ata.unit.three.project.expense.lambda;

import ata.unit.three.project.App;
import ata.unit.three.project.expense.lambda.models.Expense;
import ata.unit.three.project.expense.service.DaggerExpenseServiceComponent;
import ata.unit.three.project.expense.service.ExpenseService;
import ata.unit.three.project.expense.service.ExpenseServiceComponent;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kenzie.ata.ExcludeFromJacocoGeneratedReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ExcludeFromJacocoGeneratedReport
public class UpdateExpense implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    static final Logger log = LogManager.getLogger();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        // Logging the request json to make debugging easier.
        log.info(gson.toJson(input));

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

        String expenseId = input.getPathParameters().get("expenseId");

        // Your Code Here
//        ExpenseService expenseService = App.expenseService();
        ExpenseServiceComponent dagger = DaggerExpenseServiceComponent.create();
        ExpenseService expenseService = dagger.expenseService();
        Expense expense = gson.fromJson(input.getBody(), Expense.class);


        try {
            expenseService.updateExpense(expenseId, expense);
            log.info(expenseId);

            return response
                    .withStatusCode(204)
                    .withBody(expenseId);
        } catch (Exception e) {
            log.info(expenseId);

            return response
                    .withStatusCode(404);
        }
    }
}
