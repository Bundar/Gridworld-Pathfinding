import pygame
import sys

if __name__ == "__main__":
    
    # Retrive file name for input data
    if(len(sys.argv) < 2):
        print ("One arguement required: python showGrid.py <gridfile>")
        exit()
    
    filename = sys.argv[1]
    pathpoints = []
    startCell = []
    endCell = []
    if len(sys.argv) == 3:
        filenamePath = sys.argv[2]
        # read path form file
        pathlines = [line.rstrip('\n') for line in open(filenamePath)]
        
        for line in range(2, len(pathlines)):
            pathpoints.append(list(map(float, pathlines[line].split(' '))))
        startCell = list(map(float, pathlines[0].split(' ')))
        endCell = list(map(float, pathlines[1].split(' ')))
        print('StartCell: ', startCell)
        print('endCell: ', endCell)
        print('Path: ', pathpoints)

    print(filename)

    # Read data
    lines = [line.rstrip('\n') for line in open(filename)]
    grid = []
    for line in range(2, len(lines)):
        grid.append(list(map(float, lines[line].split(' '))))


    
    rows = int(lines[0])
    cols = int(lines[1])
# Define some colors
BLACK = (0, 0, 0)
WHITE = (255, 255, 255)
GREEN = (0, 255, 0)
RED = (255, 0, 0)
BLUE = (0,0,255)
 
# This sets the margin between each cell
MARGIN = 0
 
# Initialize pygame
pygame.init()
 
# Set the HEIGHT and WIDTH of the screen
WINDOW_SIZE = [1010, 1010]

# This sets the WIDTH and HEIGHT of each grid location
WIDTH = WINDOW_SIZE[1]/cols
HEIGHT = WINDOW_SIZE[0]/rows

screen = pygame.display.set_mode(WINDOW_SIZE)
 
# Set title of screen
pygame.display.set_caption('Grid World Visualizer: '+ filename)
 
# Loop until the user clicks the close button.
done = False

clock = pygame.time.Clock()
 
# -------- Main Program Loop -----------
while not done:
    for event in pygame.event.get():  # User did something
        if event.type == pygame.QUIT:  # If user clicked close
            print('exit')
            done = True  # Flag that we are done so we exit this loop
    # Set the screen background
    screen.fill(BLACK)

    # Draw the grid
    for row in range(rows):
        for column in range(cols):
            color = WHITE
            if grid[row][column] == 1:
                color = BLACK
            if [row, column] in pathpoints:
            	color = GREEN
            if [row, column] == startCell:
            	color = BLUE
            if [row, column] == endCell:
            	color = RED
            pygame.draw.rect(screen,
                             color,
                             [(MARGIN + WIDTH) * column + MARGIN,
                              (MARGIN + HEIGHT) * row + MARGIN,
                              WIDTH,
                              HEIGHT])
            
 
    
 
    # Limit to 60 frames per second
    clock.tick(60)
 
    # Go ahead and update the screen with what we've drawn.
    pygame.display.flip()
 
# Be IDLE friendly. If you forget this line, the program will 'hang'
# on exit.
pygame.quit()
sys.exit()