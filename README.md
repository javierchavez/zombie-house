# Zombie House

This is a game. we need more description.

## Documentation
The JavaDocs can be found [here](http://javierchavez.github.io/zombie-house/)
The Project specification can be found [here](http://cs.unm
.edu/~joel/cs351/notes/CS-351-05-ZombieHouse.pdf)
## Models
- The character controlled by the player
- The house where the character moves
- obstacles
- zombies

## Controllers
- player input
- Controller to perform actions on the player’s character (move, attack)

## Views
- Renderer – Render the objects onto the screen

## Main
- Main loop is in `ZombieHouse.java` events are passed from here to controller


####Setting up Intellij
1. On the startup screen, click the option, **Check out from Version Control**
2. Choose **git** from the dropdown menu
3. Paste the Git repo URL [git@github.com:javierchavez/zombie-house.git]
4. Choose the dir. for the repo to live.
5. Select yes when it asks if you want to **create an intellij proj.**


## Other

Create the files.txt (if out of date)
```bash
    find . -name "*.java" > files.txt
```

Permission fix (if not already set)
```bash
    chmod u+rwx make.bash run.bash
```

Run
```bash
    ./make.bash && ./run.bash
```

---
######Authors
- Alexander Baker <alexebaker@unm.edu>
- Erin Sosebee <esosebee@unm.edu>
- Javier Chavez <javierc@cs.unm.edu>

