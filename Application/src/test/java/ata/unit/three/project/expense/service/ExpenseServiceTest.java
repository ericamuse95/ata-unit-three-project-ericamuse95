package ata.unit.three.project.expense.service;

import ata.unit.three.project.expense.dynamodb.ExpenseItem;
import ata.unit.three.project.expense.dynamodb.ExpenseItemList;
import ata.unit.three.project.expense.dynamodb.ExpenseServiceRepository;
import ata.unit.three.project.expense.lambda.models.Expense;
import ata.unit.three.project.expense.service.exceptions.InvalidDataException;
import ata.unit.three.project.expense.service.exceptions.ItemNotFoundException;
import ata.unit.three.project.expense.service.model.ExpenseItemConverter;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.*;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

class ExpenseServiceTest {

    private final MockNeat mockNeat = MockNeat.threadLocal();

    /** ------------------------------------------------------------------------
     *  expenseService.getExpenseById
     *  ------------------------------------------------------------------------ **/

    @Test
    void get_expense_by_id() {
        //GIVEN
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = new ExpenseItem();
        String id = UUID.randomUUID().toString();
        expenseItem.setId(id);
        expenseItem.setEmail(mockNeat.emails().val());
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());

        //WHEN
        when(expenseServiceRepository.getExpenseById(id)).thenReturn(expenseItem);

        //THEN
        ExpenseItem returnedExpenseItem = expenseService.getExpenseById(id);
        Assertions.assertEquals(returnedExpenseItem.getId(), expenseItem.getId());
        Assertions.assertEquals(returnedExpenseItem.getEmail(), expenseItem.getEmail());
        Assertions.assertEquals(returnedExpenseItem.getTitle(), expenseItem.getTitle());
        Assertions.assertEquals(returnedExpenseItem.getExpenseDate(), expenseItem.getExpenseDate());
    }

    // Write additional tests here
    @Test
    void get_expense_by_id_null_id_throws_exception() {
        //GIVEN
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = new ExpenseItem();
        String id = null;
        expenseItem.setId(id);
        expenseItem.setEmail(mockNeat.emails().val());
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());

        //WHEN
        when(expenseServiceRepository.getExpenseById(id)).thenThrow(InvalidDataException.class);

        //THEN
        assertThrows(InvalidDataException.class, () -> expenseService.getExpenseById(id));

    }

    @Test
    void get_expense_by_id_invalid_id_throws_exception() {
        //GIVEN
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = new ExpenseItem();
        String id = "invalid id";
        expenseItem.setId(id);
        expenseItem.setEmail(mockNeat.emails().val());
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());

        //WHEN
        when(expenseServiceRepository.getExpenseById(id)).thenThrow(InvalidDataException.class);

        //THEN
        assertThrows(InvalidDataException.class, () -> expenseService.getExpenseById(id));
    }

    /** ------------------------------------------------------------------------
     *  expenseService.getExpensesByEmail
     *  ------------------------------------------------------------------------ **/

    @Test
    void get_expenses_by_email() {
        //GIVEN
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = new ExpenseItem();
        String id = UUID.randomUUID().toString();
        String email = mockNeat.emails().val();
        expenseItem.setId(id);
        expenseItem.setEmail(email);
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());

        List<ExpenseItem> expenseItemList = Collections.singletonList(expenseItem);

        //WHEN
        when(expenseServiceRepository.getExpensesByEmail(email)).thenReturn(expenseItemList);

        //THEN
        List<ExpenseItem> returnedExpenseList = expenseService.getExpensesByEmail(email);
        assertEquals(returnedExpenseList.size(), 1);
        assertEquals(returnedExpenseList.get(0).getId(), id);
        assertEquals(returnedExpenseList.get(0).getEmail(), email);
    }

    // Write additional tests here
    @Test
    void get_expenses_by_email_throws_exception() {
        //GIVEN
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = new ExpenseItem();
        String id = UUID.randomUUID().toString();
        String email = null;
        expenseItem.setId(id);
        expenseItem.setEmail(email);
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());

        //WHEN
        when(expenseServiceRepository.getExpensesByEmail(email)).thenThrow(InvalidDataException.class);

        //THEN
        assertThrows(InvalidDataException.class, () -> expenseService.getExpensesByEmail(email));
    }

    /** ------------------------------------------------------------------------
     *  expenseService.updateExpense
     *  ------------------------------------------------------------------------ **/

    // Write additional tests here
    @Test
    void update_expense() {
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = new ExpenseItem();
        String id = UUID.randomUUID().toString();
        expenseItem.setId(id);
        expenseItem.setEmail(mockNeat.emails().val());
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());

        ExpenseItem newExpenseItem = new ExpenseItem();
        String newId = UUID.randomUUID().toString();
        newExpenseItem.setId(newId);
        newExpenseItem.setEmail(mockNeat.emails().val());
        newExpenseItem.setExpenseDate(Instant.now().toString());
        newExpenseItem.setTitle(mockNeat.strings().val());

        Expense newExpense = new Expense(newExpenseItem.getEmail(), newExpenseItem.getTitle(), newExpenseItem.getAmount());

        when(expenseServiceRepository.getExpenseById(anyString())).thenReturn(new ExpenseItem());

        expenseService.updateExpense(id, newExpense);
    }
    @Test
    void update_expense_item_null_throws_exception() {
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = new ExpenseItem();
        String id = UUID.randomUUID().toString();
        expenseItem.setId(id);
        expenseItem.setEmail(mockNeat.emails().val());
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());

        ExpenseItem newExpenseItem = new ExpenseItem();
        String newId = null;
        newExpenseItem.setId(newId);
        newExpenseItem.setEmail(mockNeat.emails().val());
        newExpenseItem.setExpenseDate(Instant.now().toString());
        newExpenseItem.setTitle(mockNeat.strings().val());

        Expense newExpense = new Expense(newExpenseItem.getEmail(), newExpenseItem.getTitle(), newExpenseItem.getAmount());

        assertThrows(ItemNotFoundException.class, () -> expenseService.updateExpense(id, newExpense));
    }

    @Test
    void update_expense_invalid_id_throws_exception() {
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = new ExpenseItem();
        String id = "invalid id";
        expenseItem.setId(id);
        expenseItem.setEmail(mockNeat.emails().val());
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());

        ExpenseItem newExpenseItem = new ExpenseItem();
        String newId = UUID.randomUUID().toString();
        newExpenseItem.setId(newId);
        newExpenseItem.setEmail(mockNeat.emails().val());
        newExpenseItem.setExpenseDate(Instant.now().toString());
        newExpenseItem.setTitle(mockNeat.strings().val());

        Expense newExpense = new Expense(newExpenseItem.getEmail(), newExpenseItem.getTitle(), newExpenseItem.getAmount());

        assertThrows(InvalidDataException.class, () -> expenseService.updateExpense(id, newExpense));

    }

    @Test
    void update_expense_null_id_throws_exception() {
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = new ExpenseItem();
        String id = null;
        expenseItem.setId(id);
        expenseItem.setEmail(mockNeat.emails().val());
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());

        ExpenseItem newExpenseItem = new ExpenseItem();
        String newId = UUID.randomUUID().toString();
        newExpenseItem.setId(newId);
        newExpenseItem.setEmail(mockNeat.emails().val());
        newExpenseItem.setExpenseDate(Instant.now().toString());
        newExpenseItem.setTitle(mockNeat.strings().val());

        Expense newExpense = new Expense(newExpenseItem.getEmail(), newExpenseItem.getTitle(), newExpenseItem.getAmount());

        assertThrows(InvalidDataException.class, () -> expenseService.updateExpense(id, newExpense));

    }

    /** ------------------------------------------------------------------------
     *  expenseService.deleteExpense
     *  ------------------------------------------------------------------------ **/

    // Write additional tests here
    @Test
    void delete_expense() {
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = new ExpenseItem();
        String id = UUID.randomUUID().toString();
        expenseItem.setId(id);
        expenseItem.setEmail(mockNeat.emails().val());
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());

        expenseService.deleteExpense(id);
    }

    @Test
    void delete_expense_invalid_id_throws_exception() {
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = new ExpenseItem();
        String id = "invalid id";
        expenseItem.setId(id);
        expenseItem.setEmail(mockNeat.emails().val());
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());

        assertThrows(InvalidDataException.class, () -> expenseService.deleteExpense(id));
    }

    @Test
    void delete_expense_null_id_throws_exception() {
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = new ExpenseItem();
        String id = null;
        expenseItem.setId(id);
        expenseItem.setEmail(mockNeat.emails().val());
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());

        assertThrows(InvalidDataException.class, () -> expenseService.deleteExpense(id));

    }

    /** ------------------------------------------------------------------------
     *  expenseService.createExpense
     *  ------------------------------------------------------------------------ **/

    // Write additional tests here
    @Test
    void create_expense() {
        //GIVEN
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);
        Expense expense;

        ExpenseItem expenseItem = new ExpenseItem();
        String id = UUID.randomUUID().toString();
        expenseItem.setId(id);
        expenseItem.setEmail(mockNeat.emails().val());
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());
        expenseItem.setAmount(mockNeat.doubles().val());

        expense = new Expense(expenseItem.getEmail(), expenseItem.getTitle(), expenseItem.getAmount());

        when(expenseItemConverter.convert(expense)).thenReturn(expenseItem);

        String expenseOutput = expenseService.createExpense(expense);

        assertEquals(id, expenseOutput);
    }

    @Test
    void create_expense_amount_null_throws_exception () {
        //GIVEN
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);
        Expense expense;

        ExpenseItem expenseItem = new ExpenseItem();
        String id = UUID.randomUUID().toString();
        expenseItem.setId(id);
        expenseItem.setEmail(mockNeat.emails().val());
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());
        expenseItem.setAmount(null);

        expense = new Expense(expenseItem.getEmail(), expenseItem.getTitle(), expenseItem.getAmount());

        when(expenseItemConverter.convert(expense)).thenReturn(expenseItem);

        assertThrows(InvalidDataException.class, () -> expenseService.createExpense(expense));
    }

    /** ------------------------------------------------------------------------
     *  expenseService.addExpenseItemToList
     *  ------------------------------------------------------------------------ **/
    // Write additional tests here
    @Test
    void add_expense_item_to_list() {
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = new ExpenseItem();
        String id = UUID.randomUUID().toString();
        String email = mockNeat.emails().val();
        expenseItem.setId(id);
        expenseItem.setEmail(email);
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());
        expenseItem.setAmount(mockNeat.doubles().val());

        //WHEN
        ExpenseItemList expenseItemList = new ExpenseItemList();
        String expenseListId = mockNeat.strings().val();
        expenseItemList.setEmail(email);
        expenseItemList.setTitle(mockNeat.strings().val());
        expenseItemList.setId(expenseListId);

        when(expenseServiceRepository.getExpenseById(id)).thenReturn(expenseItem);
        when(expenseServiceRepository.getExpenseListById(expenseListId)).thenReturn(expenseItemList);

        expenseService.addExpenseItemToList(expenseListId, expenseItem.getId());

        verify(expenseServiceRepository).addExpenseItemToList(expenseListId, expenseItem);
    }

    @Test
    void add_expense_item_to_list_expense_item_null_throws_exception() {
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = null;
        String id = UUID.randomUUID().toString();
        String email = mockNeat.emails().val();

        //WHEN
        ExpenseItemList expenseItemList = new ExpenseItemList();
        String expenseListId = mockNeat.strings().val();
        expenseItemList.setEmail(email);
        expenseItemList.setTitle(mockNeat.strings().val());
        expenseItemList.setId(expenseListId);

        when(expenseServiceRepository.getExpenseById(id)).thenReturn(expenseItem);
        when(expenseServiceRepository.getExpenseListById(expenseListId)).thenReturn(expenseItemList);

        assertThrows(ItemNotFoundException.class, () -> expenseService.addExpenseItemToList(expenseListId, id));
    }

    @Test
    void add_expense_item_to_list_expense_item_list_null_throws_exception() {
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = new ExpenseItem();
        String id = UUID.randomUUID().toString();
        String email = mockNeat.emails().val();
        expenseItem.setId(id);
        expenseItem.setEmail(email);
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());
        expenseItem.setAmount(mockNeat.doubles().val());

        //WHEN
        ExpenseItemList expenseItemList = null;
        String expenseListId = mockNeat.strings().val();

        when(expenseServiceRepository.getExpenseById(id)).thenReturn(expenseItem);
        when(expenseServiceRepository.getExpenseListById(expenseListId)).thenReturn(expenseItemList);

        assertThrows(ItemNotFoundException.class, () -> expenseService.addExpenseItemToList(expenseListId, id));
    }

    @Test
    void add_expense_item_to_list_expense_id_null_throws_exception() {
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = new ExpenseItem();
        String id = null;
        String email = mockNeat.emails().val();
        expenseItem.setId(id);
        expenseItem.setEmail(email);
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());
        expenseItem.setAmount(mockNeat.doubles().val());

        //WHEN
        ExpenseItemList expenseItemList = new ExpenseItemList();
        String expenseListId = mockNeat.strings().val();
        expenseItemList.setEmail(email);
        expenseItemList.setTitle(mockNeat.strings().val());
        expenseItemList.setId(expenseListId);

        when(expenseServiceRepository.getExpenseById(id)).thenReturn(expenseItem);
        when(expenseServiceRepository.getExpenseListById(expenseListId)).thenReturn(expenseItemList);

        assertThrows(ItemNotFoundException.class, () -> expenseService.addExpenseItemToList(expenseListId, id));
    }

    @Test
    void add_expense_item_to_list_list_id_null_throws_exception() {
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = new ExpenseItem();
        String id = UUID.randomUUID().toString();
        String email = mockNeat.emails().val();
        expenseItem.setId(id);
        expenseItem.setEmail(email);
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());
        expenseItem.setAmount(mockNeat.doubles().val());

        //WHEN
        ExpenseItemList expenseItemList = new ExpenseItemList();
        String expenseListId = null;
        expenseItemList.setEmail(email);
        expenseItemList.setTitle(mockNeat.strings().val());
        expenseItemList.setId(expenseListId);

        when(expenseServiceRepository.getExpenseById(id)).thenReturn(expenseItem);
        when(expenseServiceRepository.getExpenseListById(expenseListId)).thenReturn(expenseItemList);

        assertThrows(ItemNotFoundException.class, () -> expenseService.addExpenseItemToList(expenseListId, id));
    }

    @Test
    void add_expense_item_to_list_email_does_not_match_throws_exception() {
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = new ExpenseItem();
        String id = UUID.randomUUID().toString();
        String email = mockNeat.emails().val();
        expenseItem.setId(id);
        expenseItem.setEmail(email);
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());
        expenseItem.setAmount(mockNeat.doubles().val());

        //WHEN
        ExpenseItemList expenseItemList = new ExpenseItemList();
        String expenseListId = mockNeat.strings().val();
        expenseItemList.setEmail("test@test.com");
        expenseItemList.setTitle(mockNeat.strings().val());
        expenseItemList.setId(expenseListId);

        when(expenseServiceRepository.getExpenseById(id)).thenReturn(expenseItem);
        when(expenseServiceRepository.getExpenseListById(expenseListId)).thenReturn(expenseItemList);

        assertThrows(ItemNotFoundException.class, () -> expenseService.addExpenseItemToList(expenseListId, id));
    }

    @Test
    void add_expense_item_to_list_item_exists_throws_exception() {
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = new ExpenseItem();
        String id = UUID.randomUUID().toString();
        String email = mockNeat.emails().val();
        expenseItem.setId(id);
        expenseItem.setEmail(email);
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());
        expenseItem.setAmount(mockNeat.doubles().val());

        //WHEN
        ExpenseItemList expenseItemList = new ExpenseItemList();
        String expenseListId = mockNeat.strings().val();
        expenseItemList.setEmail(email);
        expenseItemList.setTitle(mockNeat.strings().val());
        expenseItemList.setId(expenseListId);

        when(expenseServiceRepository.getExpenseById(id)).thenReturn(expenseItem);
        when(expenseServiceRepository.getExpenseListById(expenseListId)).thenReturn(expenseItemList);

        expenseItemList.setExpenseItems(Collections.singletonList(expenseItem));

        assertThrows(ItemNotFoundException.class, () -> expenseService.addExpenseItemToList(expenseListId, id));
        assertNotNull(expenseItemList.getExpenseItems());
        assertTrue(expenseItemList.getExpenseItems().contains(expenseItem));
    }

    /** ------------------------------------------------------------------------
     *  expenseService.removeExpenseItemFromList
     *  ------------------------------------------------------------------------ **/

    // Write additional tests here
    @Test
    void remove_expense_item_from_list() {
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = new ExpenseItem();
        String id = UUID.randomUUID().toString();
        String email = mockNeat.emails().val();
        expenseItem.setId(id);
        expenseItem.setEmail(email);
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());
        expenseItem.setAmount(mockNeat.doubles().val());

        //WHEN
        ExpenseItemList expenseItemList = new ExpenseItemList();
        String expenseListId = mockNeat.strings().val();
        expenseItemList.setEmail(email);
        expenseItemList.setTitle(mockNeat.strings().val());
        expenseItemList.setId(expenseListId);

        when(expenseServiceRepository.getExpenseById(id)).thenReturn(expenseItem);
        when(expenseServiceRepository.getExpenseListById(expenseListId)).thenReturn(expenseItemList);

        expenseItemList.setExpenseItems(Collections.singletonList(expenseItem));
        expenseService.removeExpenseItemFromList(expenseListId, expenseItem.getId());

        verify(expenseServiceRepository).removeExpenseItemToList(expenseListId, expenseItem);
    }

    @Test
    void remove_expense_item_from_list_expense_id_null_throws_exception() {
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = new ExpenseItem();
        String id = null;
        String email = mockNeat.emails().val();
        expenseItem.setId(id);
        expenseItem.setEmail(email);
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());
        expenseItem.setAmount(mockNeat.doubles().val());

        //WHEN
        ExpenseItemList expenseItemList = new ExpenseItemList();
        String expenseListId = mockNeat.strings().val();
        expenseItemList.setEmail(email);
        expenseItemList.setTitle(mockNeat.strings().val());
        expenseItemList.setId(expenseListId);

        when(expenseServiceRepository.getExpenseById(id)).thenReturn(expenseItem);
        when(expenseServiceRepository.getExpenseListById(expenseListId)).thenReturn(expenseItemList);

        expenseItemList.setExpenseItems(Collections.singletonList(expenseItem));

        assertThrows(ItemNotFoundException.class, () -> expenseService.removeExpenseItemFromList(expenseListId, expenseItem.getId()));
    }

    @Test
    void remove_expense_item_from_list_id_null_throws_exception() {
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = new ExpenseItem();
        String id = UUID.randomUUID().toString();
        String email = mockNeat.emails().val();
        expenseItem.setId(id);
        expenseItem.setEmail(email);
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());
        expenseItem.setAmount(mockNeat.doubles().val());

        //WHEN
        ExpenseItemList expenseItemList = new ExpenseItemList();
        String expenseListId = null;
        expenseItemList.setEmail(email);
        expenseItemList.setTitle(mockNeat.strings().val());
        expenseItemList.setId(expenseListId);

        when(expenseServiceRepository.getExpenseById(id)).thenReturn(expenseItem);
        when(expenseServiceRepository.getExpenseListById(expenseListId)).thenReturn(expenseItemList);

        expenseItemList.setExpenseItems(Collections.singletonList(expenseItem));

        assertThrows(ItemNotFoundException.class, () -> expenseService.removeExpenseItemFromList(expenseListId, expenseItem.getId()));
    }

    @Test
    void remove_expense_item_from_list_expense_item_null_throws_exception() {
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = null;
        String id = UUID.randomUUID().toString();
        String email = mockNeat.emails().val();

        //WHEN
        ExpenseItemList expenseItemList = new ExpenseItemList();
        String expenseListId = mockNeat.strings().val();
        expenseItemList.setEmail(email);
        expenseItemList.setTitle(mockNeat.strings().val());
        expenseItemList.setId(expenseListId);

        when(expenseServiceRepository.getExpenseById(id)).thenReturn(expenseItem);
        when(expenseServiceRepository.getExpenseListById(expenseListId)).thenReturn(expenseItemList);

        expenseItemList.setExpenseItems(Collections.singletonList(expenseItem));

        assertThrows(ItemNotFoundException.class, () -> expenseService.removeExpenseItemFromList(expenseListId, id));
    }
    @Test
    void remove_expense_item_from_list_expense_item_list_null_throws_exception() {
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = new ExpenseItem();
        String id = UUID.randomUUID().toString();
        String email = mockNeat.emails().val();
        expenseItem.setId(id);
        expenseItem.setEmail(email);
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());
        expenseItem.setAmount(mockNeat.doubles().val());

        //WHEN
        ExpenseItemList expenseItemList = null;
        String expenseListId = mockNeat.strings().val();

        when(expenseServiceRepository.getExpenseById(id)).thenReturn(expenseItem);
        when(expenseServiceRepository.getExpenseListById(expenseListId)).thenReturn(expenseItemList);

        assertThrows(ItemNotFoundException.class, () -> expenseService.removeExpenseItemFromList(expenseListId, expenseItem.getId()));
    }

    @Test
    void remove_expense_item_from_list_email_does_not_match_throws_exception() {
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = new ExpenseItem();
        String id = UUID.randomUUID().toString();
        String email = mockNeat.emails().val();
        expenseItem.setId(id);
        expenseItem.setEmail(email);
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());
        expenseItem.setAmount(mockNeat.doubles().val());

        //WHEN
        ExpenseItemList expenseItemList = new ExpenseItemList();
        String expenseListId = mockNeat.strings().val();
        expenseItemList.setEmail("sample@test.com");
        expenseItemList.setTitle(mockNeat.strings().val());
        expenseItemList.setId(expenseListId);

        when(expenseServiceRepository.getExpenseById(id)).thenReturn(expenseItem);
        when(expenseServiceRepository.getExpenseListById(expenseListId)).thenReturn(expenseItemList);

        expenseItemList.setExpenseItems(Collections.singletonList(expenseItem));

        assertThrows(ItemNotFoundException.class, () -> expenseService.removeExpenseItemFromList(expenseListId, expenseItem.getId()));
    }

    @Test
    void remove_expense_item_from_list_item_does_not_exist_throws_exception() {
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = new ExpenseItem();
        String id = UUID.randomUUID().toString();
        String email = mockNeat.emails().val();
        expenseItem.setId(id);
        expenseItem.setEmail(email);
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());
        expenseItem.setAmount(mockNeat.doubles().val());

        //WHEN
        ExpenseItemList expenseItemList = new ExpenseItemList();
        String expenseListId = mockNeat.strings().val();
        expenseItemList.setEmail(email);
        expenseItemList.setTitle(mockNeat.strings().val());
        expenseItemList.setId(expenseListId);

        when(expenseServiceRepository.getExpenseById(id)).thenReturn(expenseItem);
        when(expenseServiceRepository.getExpenseListById(expenseListId)).thenReturn(expenseItemList);

        expenseItemList.setExpenseItems(Collections.singletonList(expenseItem));

        ExpenseItem expenseItem2 = new ExpenseItem();
        String id2 = UUID.randomUUID().toString();
        String email2 = mockNeat.emails().val();
        expenseItem2.setId(id2);
        expenseItem2.setEmail(email);
        expenseItem2.setExpenseDate(Instant.now().toString());
        expenseItem2.setTitle(mockNeat.strings().val());
        expenseItem2.setAmount(mockNeat.doubles().val());

        //WHEN
        ExpenseItemList expenseItemList2 = new ExpenseItemList();
        String expenseListId2 = mockNeat.strings().val();
        expenseItemList2.setEmail(email2);
        expenseItemList2.setTitle(mockNeat.strings().val());
        expenseItemList2.setId(expenseListId);


        when(expenseServiceRepository.getExpenseById(id2)).thenReturn(expenseItem2);
        when(expenseServiceRepository.getExpenseListById(expenseListId2)).thenReturn(expenseItemList2);

        assertThrows(ItemNotFoundException.class, () -> expenseService.removeExpenseItemFromList(expenseListId, expenseItem2.getId()));
        assertFalse(expenseItemList.getExpenseItems().contains(expenseItem2));
    }


    /** ------------------------------------------------------------------------
     *  expenseService.getExpenseListByEmail
     *  ------------------------------------------------------------------------ **/

    @Test
    void get_expense_list_by_email() {
        //GIVEN
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = new ExpenseItem();
        String id = UUID.randomUUID().toString();
        String email = mockNeat.emails().val();
        expenseItem.setId(id);
        expenseItem.setEmail(email);
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());

        //WHEN
        ExpenseItemList expenseItemList = new ExpenseItemList();
        String expenseListId = mockNeat.strings().val();
        expenseItemList.setEmail(email);
        expenseItemList.setTitle(mockNeat.strings().val());
        expenseItemList.setId(expenseListId);
        expenseItemList.setExpenseItems(Collections.singletonList(expenseItem));
        List<ExpenseItemList> list = Collections.singletonList(expenseItemList);

        when(expenseServiceRepository.getExpenseListsByEmail(anyString())).thenReturn(list);

        //THEN
        List<ExpenseItemList> returnedExpenseList = expenseService.getExpenseListByEmail(email);
        assertEquals(returnedExpenseList.size(), 1);
        assertEquals(returnedExpenseList.get(0).getId(), expenseListId);
        assertEquals(returnedExpenseList.get(0).getEmail(), email);
        assertNotNull(expenseItemList.getExpenseItems());
        assertTrue(expenseItemList.getExpenseItems().size() > 0);
    }

    // Write additional tests here
    @Test
    void get_expense_list_by_email_throws_exception() {
        //GIVEN
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);

        ExpenseItem expenseItem = new ExpenseItem();
        String id = UUID.randomUUID().toString();
        String email = mockNeat.emails().val();
        expenseItem.setId(id);
        expenseItem.setEmail(email);
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());

        //WHEN
        ExpenseItemList expenseItemList = new ExpenseItemList();
        String expenseListId = mockNeat.strings().val();
        expenseItemList.setEmail(email);
        expenseItemList.setTitle(mockNeat.strings().val());
        expenseItemList.setId(expenseListId);
        expenseItemList.setExpenseItems(Collections.singletonList(expenseItem));
        List<ExpenseItemList> list = Collections.singletonList(expenseItemList);

        when(expenseServiceRepository.getExpenseListsByEmail(anyString())).thenReturn(list);

        //THEN
        assertThrows(InvalidDataException.class, () -> expenseService.getExpenseListByEmail(""));

    }

    /** ------------------------------------------------------------------------
     *  expenseService.createExpenseList
     *  ------------------------------------------------------------------------ **/

    @Test
    void create_expense_list() {
        ExpenseServiceRepository expenseServiceRepository = mock(ExpenseServiceRepository.class);
        ExpenseItemConverter expenseItemConverter = mock(ExpenseItemConverter.class);
        ExpenseService expenseService = new ExpenseService(expenseServiceRepository, expenseItemConverter);
        Expense expense;

        ExpenseItem expenseItem = new ExpenseItem();
        String id = UUID.randomUUID().toString();
        String email = mockNeat.emails().val();
        expenseItem.setId(id);
        expenseItem.setEmail(email);
        expenseItem.setExpenseDate(Instant.now().toString());
        expenseItem.setTitle(mockNeat.strings().val());
        expenseItem.setAmount(mockNeat.doubles().val());

        ExpenseItemList expenseItemList = new ExpenseItemList();
        String expenseListId = randomUUID().toString();
        expenseItemList.setEmail(email);
        expenseItemList.setTitle(mockNeat.strings().val());
        expenseItemList.setId(expenseListId);

        expenseService.createExpenseList(email, expenseItem.getTitle());
    }
}