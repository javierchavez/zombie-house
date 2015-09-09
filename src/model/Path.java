package model;


import java.util.List;

public interface Path
{
  List<Object> find (House board, Object start, Object end);
}
