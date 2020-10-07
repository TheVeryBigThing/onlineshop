package mapper;

import com.thing.dao.mapper.ProductRowMapper;
import com.thing.entity.Product;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRowMapperTest {
    @Test
    public void testMapRow() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getInt("id")).thenReturn(123);
        when(mockResultSet.getString("name")).thenReturn("John");
        when(mockResultSet.getDouble("price")).thenReturn(150.55);
        when(mockResultSet.getString("expireDate")).thenReturn("1999-05-07");

        Product product = ProductRowMapper.mapRow(mockResultSet);

        assertEquals(123, product.getId());
        assertEquals("John", product.getName());
        assertEquals(150.55, product.getPrice(), 0.00001);
        LocalDate date = LocalDate.of(1999, 05, 07);
        assertEquals(date, product.getExpireDate());

    }
}