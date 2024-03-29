package ata.unit.three.project.expense.service;

import ata.unit.three.project.expense.dynamodb.ExpenseItem;
import ata.unit.three.project.expense.dynamodb.ExpenseItemList;
import ata.unit.three.project.expense.dynamodb.ExpenseServiceRepository;
import ata.unit.three.project.expense.lambda.models.Expense;
import ata.unit.three.project.expense.service.exceptions.InvalidDataException;
import ata.unit.three.project.expense.service.exceptions.ItemNotFoundException;
import ata.unit.three.project.expense.service.model.ExpenseItemConverter;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

import static java.util.UUID.fromString;
import static java.util.UUID.randomUUID;

public class ExpenseService {

    private ExpenseServiceRepository expenseServiceRepository;
    private ExpenseItemConverter expenseItemConverter;

    @Inject
    public ExpenseService(ExpenseServiceRepository expenseServiceRepository,
                          ExpenseItemConverter expenseItemConverter) {
        this.expenseServiceRepository = expenseServiceRepository;
        this.expenseItemConverter = expenseItemConverter;
    }

    public ExpenseItem getExpenseById(String expenseId) {
        if (StringUtils.isEmpty(expenseId) || isInvalidUuid(expenseId)) {
            throw new InvalidDataException("Expense id is not present");
        }
        return expenseServiceRepository.getExpenseById(expenseId);
    }

    public List<ExpenseItem> getExpensesByEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            throw new InvalidDataException("Email is not present");
        }
        return expenseServiceRepository.getExpensesByEmail(email);
    }

    public String createExpense(Expense expense) {
        ExpenseItem expenseItem = expenseItemConverter.convert(expense);
        expenseServiceRepository.createExpense(expenseItem);
        if (expense.getAmount() == null) {
            throw new InvalidDataException("Amount is invalid");
        }
        return expenseItem.getId();
    }

    public void updateExpense(String expenseId, Expense updateExpense) {
        if (StringUtils.isEmpty(expenseId) || isInvalidUuid(expenseId)) {
            throw new InvalidDataException("Expense id is not present");
        }
        ExpenseItem item = expenseServiceRepository.getExpenseById(expenseId);
        if (item == null) {
            throw new ItemNotFoundException("Expense does not exist");
        }
        expenseServiceRepository.updateExpense(expenseId,
                updateExpense.getTitle(),
                updateExpense.getAmount());
    }

    public void deleteExpense(String expenseId) {
        if (StringUtils.isEmpty(expenseId) || isInvalidUuid(expenseId)) {
            throw new InvalidDataException("Expense id is not present");
        }
        expenseServiceRepository.deleteExpense(expenseId);
    }

    public String createExpenseList(String email, String title) {
        String expenseListId = randomUUID().toString();
        expenseServiceRepository.createExpenseList(expenseListId, email, title);
        return expenseListId;
    }

    public void addExpenseItemToList(String id, String expenseId) {
        // Your Code Here

        if (expenseId == null) {
            throw new ItemNotFoundException("Expense ID is null");
        }

        if (id == null) {
            throw new ItemNotFoundException("Id is null");
        }

        ExpenseItem expenseItem = expenseServiceRepository.getExpenseById(expenseId);
        ExpenseItemList expenseItemList = expenseServiceRepository.getExpenseListById(id);

        if (expenseItem == null) {
            throw new ItemNotFoundException("ExpenseItem is null");
        }

        if (expenseItemList == null) {
            throw new ItemNotFoundException("ExpenseItemList is null");
        }

        if (!expenseItem.getEmail().equals(expenseItemList.getEmail())) {
            throw new ItemNotFoundException("ExpenseItem email does not match ExpenseItemList email");
        }

        if (expenseItemList.getExpenseItems() != null && expenseItemList.getExpenseItems().contains(expenseItem)) {
            throw new ItemNotFoundException("ExpenseItem is already in the list");
        }

        expenseServiceRepository.addExpenseItemToList(id, expenseItem);
    }

    public void removeExpenseItemFromList(String id, String expenseId) {
        // Your Code Here

        if (expenseId == null) {
            throw new ItemNotFoundException("expenseID null");
        }

        if (id == null) {
            throw new ItemNotFoundException("id null");
        }

        ExpenseItem expenseItem = expenseServiceRepository.getExpenseById(expenseId);
        ExpenseItemList expenseItemList = expenseServiceRepository.getExpenseListById(id);

        if (expenseItem == null) {
            throw new ItemNotFoundException("ExpenseItem is null");
        }

        if (expenseItemList == null) {
            throw new ItemNotFoundException("ExpenseItemList is null");
        }

        if (!expenseItem.getEmail().equals(expenseItemList.getEmail())) {
            throw new ItemNotFoundException("ExpenseItem email does not match ExpenseItemList email");
        }

        if (!expenseItemList.getExpenseItems().contains(expenseItem)) {
            throw new ItemNotFoundException("ExpenseItem does not exist");
        }

        expenseServiceRepository.removeExpenseItemToList(id, expenseItem);
    }

    public List<ExpenseItemList> getExpenseListByEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            throw new InvalidDataException("Email is not present");
        }
        List <ExpenseItemList> expenseItemList = expenseServiceRepository.getExpenseListsByEmail(email);

        for (ExpenseItemList itemList : expenseItemList) {
            if (itemList.getExpenseItems() != null && itemList.getExpenseItems().size() > 0) {
                Collections.sort(itemList.getExpenseItems(), new SortItems().reversed());
            }
        }
        return expenseItemList;
    }

    private boolean isInvalidUuid(String uuid) {
        try {
            fromString(uuid);
        } catch (IllegalArgumentException exception) {
            return true;
        }
        return false;
    }
}