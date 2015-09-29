# Zombie House

This is a game in which the player is placed in a dark, unfamiliar, and zombie-infested house and only one way to escape.
The player must navigate through the house's dangerous hallways and find the one exit before the zombies catch them and
eat their brains!

## Documentation
The JavaDocs can be found [here](http://javierchavez.github.io/zombie-house/)
The Project specification can be found [here](http://cs.unm
.edu/~joel/cs351/notes/CS-351-05-ZombieHouse.pdf)
## Models
- The character controlled by the player
- The house where the character moves
- Obstacles
- Zombies

## Controllers
- Player input
- Controller to perform actions on the player’s character (move, attack)

## Views
- Renderer – Render the objects onto the screen

## Main
- Main loop is in `ZombieHouse.java`. Events are passed from here to controller

## Menu Options
- Play - Resumes a game
- Restart - Reset the house to it's previous saved state
- Exit - Quits the game
- Settings - Opens a settings menu to change the current game settings
- Generate - Generates a house using the current game settings
- Level 1 through 5 - Generates a house based on pre-defined settings

The menu can be opened any time in game by pressing SPACE. Cycle through the menu's options by using the left and right
arrow keys.


## Other


Permission fix (if not already set)
```bash
    chmod u+rwx game
```

Run
```bash
    ./game run
```

Other commands
```bash
    ./game build

    ./game clean
```

####Setting up Intellij
1. On the startup screen, click the option, **Check out from Version Control**
2. Choose **git** from the dropdown menu
3. Paste the Git repo URL [git@github.com:javierchavez/zombie-house.git]
4. Choose the dir. for the repo to live.
5. Select yes when it asks if you want to **create an intellij proj.**


---
######Authors
- Alexander Baker <alexebaker@unm.edu>
- Erin Sosebee <esosebee@unm.edu>
- Javier Chavez <javierc@cs.unm.edu>

