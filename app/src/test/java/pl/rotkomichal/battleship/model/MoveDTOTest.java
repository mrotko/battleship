package pl.rotkomichal.battleship.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by michal on 15.01.18.
 */
public class MoveDTOTest {
    @Test
    public void setRowId() throws Exception {
        MoveDTO moveDTO = new MoveDTO(10, 20);
        moveDTO.setRowId(50);
        assertEquals(50, moveDTO.getRowId());
    }

    @Test
    public void setFieldId() throws Exception {
        MoveDTO moveDTO = new MoveDTO(10, 20);
        moveDTO.setFieldId(17);
        assertEquals(17, moveDTO.getFieldId());
    }

    @Test
    public void getRowId() throws Exception {
        MoveDTO moveDTO = new MoveDTO(10, 20);
        assertEquals(10, moveDTO.getRowId());
    }

    @Test
    public void getFieldId() throws Exception {
        MoveDTO moveDTO = new MoveDTO(10, 20);
        assertEquals(20, moveDTO.getFieldId());
    }
}