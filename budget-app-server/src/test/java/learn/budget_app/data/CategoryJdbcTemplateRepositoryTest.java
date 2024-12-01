package learn.budget_app.data;

import learn.budget_app.models.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CategoryJdbcTemplateRepositoryTest {
    @Autowired
    CategoryJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll(){
        List<Category> categories = repository.findAll();

        assertNotNull(categories);
        assertEquals(new Category(1, "Entertainment"), categories.get(0));
        assertTrue(categories.size() >= 3);
    }

    @Test
    void shouldFindById(){
        Category category = repository.findById(2);

        Category expected = new Category(2, "Food");

        assertNotNull(category);
        assertEquals(expected,category);
    }

    @Test
    void shouldNotFind(){
        Category category = repository.findById(-999);

        assertNull(category);
    }

    @Test
    void shouldAdd(){
        Category category = new Category();
        category.setCategoryName("Electronics");

        Category actual = repository.add(category);
        assertNotNull(actual);
        assertEquals(category, actual);
    }

    @Test
    void shouldUpdateExisting(){
        Category category = new Category(4,"nothing");
        boolean actual = repository.update(category);
        Category updated = repository.findById(4);

        assertTrue(actual);
        assertEquals(category, updated);
    }

    @Test
    void shouldNotUpdateNonExisting(){
        Category category = new Category(-999,"foolishness");

        boolean actual = repository.update(category);
        assertFalse(actual);
    }

    @Test
    void shouldDelete(){
        assertTrue(repository.deleteById(3));
    }

    @Test
    void shouldNotDeleteNonExisting(){
        assertFalse(repository.deleteById(-99999));
    }

    @Test
    void shouldNotDeleteReferencedCategory(){
        assertFalse(repository.deleteById(4));
    }
}