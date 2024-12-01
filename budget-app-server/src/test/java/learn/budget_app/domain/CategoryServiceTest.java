package learn.budget_app.domain;

import learn.budget_app.data.CategoryRepository;
import learn.budget_app.models.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    CategoryService service;

    @MockBean
    CategoryRepository repository;

    @Test
    void shouldFindAll(){
        when(repository.findAll()).thenReturn(getAll());

        List<Category> all = service.findAll();
        assertNotNull(all);
        assertEquals(3,all.size());
        assertEquals(new Category(3, "Car"), all.get(2));
    }

    @Test
    void shouldFIndById(){
        when(repository.findById(1))
                .thenReturn(new Category(1, "Entertainment"));
        Category actual = service.findById(1);
        assertNotNull(actual);
        assertEquals(new Category(1, "Entertainment"),actual);
    }

    @Test
    void shouldAdd(){
        Category category = makeCategory();
        when(repository.add(category)).thenReturn(category);
        Result<Category> result = service.add(category);
        assertTrue(result.isSuccess());
        assertEquals(category,result.getPayload());
        assertEquals("New Test Category",result.getPayload().getCategoryName());
    }

    @Test
    void shouldNotAddNull(){
        when(repository.add(null)).thenReturn(null);
        Result<Category> result = service.add(null);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("category cannot be null",result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithoutName(){
        Category category = makeCategory();
        category.setCategoryName(null);
        when(repository.add(category)).thenReturn(category);
        Result<Category> result = service.add(category);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("category name is required",result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithIdSet(){
        Category category = makeCategory();
        category.setCategoryId(4);
        when(repository.add(category)).thenReturn(category);
        Result<Category> result = service.add(category);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("categoryId cannot be set for `add` operation",result.getMessages().get(0));
    }

    @Test
    void shouldUpdate(){
        Category category = makeCategory();
        category.setCategoryId(2);
        when(repository.update(category)).thenReturn(true);
        Result<Category> result = service.update(category);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotUpdateNull(){
        when(repository.update(null)).thenReturn(false);
        Result<Category> result = service.update(null);
        assertFalse(result.isSuccess());
        assertEquals("category cannot be null",result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateWithoutName(){
        Category category = makeCategory();
        category.setCategoryName("");
        when(repository.update(category)).thenReturn(true);
        Result<Category> result = service.update(category);
        assertFalse(result.isSuccess());
        assertEquals("category name is required",result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateWithoutIDSet(){
        Category category = makeCategory();
        when(repository.update(category)).thenReturn(false);
        Result<Category> result = service.update(category);
        assertFalse(result.isSuccess());
        assertEquals("category id must be set for `update` operation",result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateNonExisting(){
        Category category = makeCategory();
        category.setCategoryId(999999);
        when(repository.update(category)).thenReturn(false);
        Result<Category> result = service.update(category);
        assertFalse(result.isSuccess());
        assertEquals("category with id 999999, not found",result.getMessages().get(0));
    }

    @Test
    void shouldDelete(){
        when(repository.deleteById(2)).thenReturn(true);
        boolean actual = service.deleteById(2);
        assertTrue(actual);
    }

    @Test
    void shouldNotDeleteNonExisting(){
        when(repository.deleteById(7777777)).thenReturn(false);
        boolean actual = service.deleteById(7777777);
        assertFalse(actual);
    }

    List<Category> getAll(){
        List<Category> all = new ArrayList<>();
        all.add(new Category(1, "Entertainment"));
        all.add(new Category(2, "Food"));
        all.add(new Category(3, "Car"));
        return all;
    }

    Category makeCategory(){
        Category category = new Category();
        category.setCategoryId(0);
        category.setCategoryName("New Test Category");

        return category;
    }
}