package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class HouseTest
{
  House house;
  Character player;

  @Before
  public void setUp () throws Exception
  {
    player = new Character();
    house = new House(player);
    house.setSize(20, 20);
  }

  @Test
  public void testSetSize () throws Exception
  {

    assertTrue(house.getHeight() == 20);
    assertTrue(house.getWidth() == 20);
    assertTrue(house.getHouse().length == 20);
    assertTrue(house.getHouse()[0].length == 20);
  }

  @Test
  public void testGetWidth () throws Exception
  {
    assertTrue(house.getWidth() == 20);
  }

  @Test
  public void testGetHeight () throws Exception
  {
    assertTrue(house.getHeight() == 20);
  }

  @Test
  public void testGetHouse () throws Exception
  {
    assertTrue(house.getHouse() != null);
  }

  @Test
  public void testGetNumRooms () throws Exception
  {

  }

  @Test
  public void testGetNumHallways () throws Exception
  {

  }

  @Test
  public void testSetMinRooms () throws Exception
  {

  }

  @Test
  public void testSetMinHallways () throws Exception
  {

  }

  @Test
  public void testGetObstacles () throws Exception
  {

  }

  @Test
  public void testGetZombies () throws Exception
  {

  }

  @Test
  public void testGetPlayer () throws Exception
  {

  }

  @Test
  public void testNeighbors () throws Exception
  {

  }

  @Test
  public void testGetTile () throws Exception
  {
    Floor f = (Floor) house.getTile(9, 17);
    assertTrue("Should not be null ", house.getTile(9, 17) != null);

    // standard matrix
    // x = col
    assertTrue("Column should be 17", f.getX() == 17);
    // y = row
    assertTrue("Row should be 9", f.getY() == 9);
  }

  @Test
  public void testPlaceTrap () throws Exception
  {
    Tile t = house.getTile(0, 0);
    house.placeTrap(t, Tile.Trap.FIRE);
    assertTrue("Should be able to place trap", house.isTrap(house.getTile(0,
                                                                          0)));
  }

  @Test
  public void testGetPlayerTile () throws Exception
  {
    player.move(1.4f, 8.7f);
    Tile t = house.getPlayerTile();

    assertTrue("Should be able to get the players tile "+
                       t.getX() + " " + t.getY(),
               t.getX() == 1 && t.getY() == 8);
  }

  @Test
  public void testIsTrap () throws Exception
  {
    Tile t = house.getTile(19, 19);
    house.placeTrap(t, Tile.Trap.FIRE);

    assertTrue(house.getTile(19, 19).getTrap() == Tile.Trap.FIRE);
  }
}