# Search and Count Hidden Motifs in BMP Images
The program opens a BMP image and searches for occurrences of a predefined motif.

## Parameters:
- color_motif: changes the color of the motif to be searched.
- count: prints the total number of occurrences found in the terminal.

## Additional parameters (if time allows):
- L/R: changes the motif’s orientation (left/right).
- reverse: instead of highlighting motifs, it highlights everything except them.
- coord: generates a JSON file with the coordinates of each occurrence and prints the count.

# Hide Motifs Randomly in BMP Images
The program opens a BMP image and hides the predefined motif in random locations.

## Parameters:
- color_motif: changes the color of the motif.
- count: defines how many motifs to hide (default = 1, max ~10–15).

## Additional parameters (if time allows):
- L/R: same as in the first functionality.
- coord: reads a JSON file with coordinates and applies the motifs accordingly.
