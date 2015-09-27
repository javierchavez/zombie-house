package controller;


import common.Speed;
import model.House;
import model.Move;
import model.SuperZombie;
import model.Tile;

import java.util.ArrayList;
import java.util.List;

public class SuperZombieController extends AbstractCharacterController<SuperZombie>
{
  private boolean initial = true; // for initial scare
  private int secIncrement = 0;
  private List<Tile> tiles;
  public SuperZombieController (House house, SuperZombie mover)
  {
    super(house, mover);
    initial = true;
  }

  @Override
  public boolean checkCollision(Move moveToCheck)
  {
    if (super.checkCollision(moveToCheck))
    {
      mover.setSpeed(0);
    }
    else
    {
      mover.move(moveToCheck.col, moveToCheck.row);
    }
    return false;
  }

  @Override
  public void update (float deltaTime)
  {
    secIncrement++;
    if (initial || secIncrement == 200)
    {

      mover.getStrategy().find(house, house.getCharacterTile(house.getSuperZombie()), house
              .getCharacterTile(house.getPlayer()));
      tiles = new ArrayList<>(50);
      tiles = mover.getStrategy().getPath();
      /*new Thread(() -> {
        while(tiles.size() > 0)
        {

          //Tile t = tiles.remove(0);
          //mover.move(t.getX(), t.getY());

          try
          {
            Thread.sleep(140);
          }
          catch (InterruptedException e)
          {

            e.printStackTrace();
          }
        }
        // house.slowReset();
      }).start();*/
      initial = false;
      secIncrement = 0;
    }

    Tile current = house.getCharacterTile(mover);
    tiles.remove(current);
    mover.setSpeed(5 * deltaTime);
    if (tiles.size() > 0)
    {
      Tile next = tiles.get(0);

      float xDir = (current.getX() - next.getX()) * -1;
      float yDir = (current.getY() - next.getY()) * -1;
      if (xDir == 0 && yDir == 0)
      {
        stopMoving();
      }
      else if(xDir == 0 && yDir > 0)
      {
        moveUp();
      }
      else if (xDir == 0 && yDir < 0)
      {
        moveDown();
      }
      else if (xDir < 0 && yDir == 0)
      {
        moveLeft();
      }
      else if (xDir > 0 && yDir == 0)
      {
        moveRight();
      }
      else if (xDir < 0 && yDir > 0)
      {
        moveUpLeft();
      }
      else if (xDir > 0 && yDir > 0)
      {
        moveUpRight();
      }
      else if (xDir < 0 && yDir < 0)
      {
        moveDownLeft();
      }
      else if (xDir > 0 && yDir < 0)
      {
        moveDownRight();
      }
      else
      {
        stopMoving();
      }
    }
    else
    {
      stopMoving();
    }

    float y = (float) (mover.getCurrentY() + mover.getSpeed() * Math.sin(Math.toRadians(mover.getRotation())));
    float x = (float) (mover.getCurrentX() + mover.getSpeed() * Math.cos(Math.toRadians(mover.getRotation())));
    checkCollision(new Move(x, y, mover.getRotation()));
  }
}
