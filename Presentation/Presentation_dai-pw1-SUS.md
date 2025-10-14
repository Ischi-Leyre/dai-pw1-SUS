---
marp: true
theme: uncover
size: 16:9
paginate: true
author: M.Ischi and A.Leyre
title: DAI - Practical Work 1
description: Practical Work 1 for the DAI course at HEIG-VD, Switzerland
---
<!-- _class: invert -->
## DAI - Praticale Work 1
 
[Link to the reposoty](https://github.com/Ischi-Leyre/dai-pw1-SUS)
 
<small>M. Ischi and A.Leyre.</small>

![w:210 h:150](/srcImage/sus_file.jpg)

---
<!-- _class: invert -->
# Objectives

- Make a programme using piccoCLI interface
- The programme must have two functionalities
- Using the additional parameters and options

---
 <!-- _class: invert -->
# Our Programs

It can hide and search a specific pattern in a bmp file


![w:120 h:120](SUS_R.png) ![w:120 h:120](SUS_L.png)

---
  <!-- _class: invert -->
# Our Fonctionnalities

- Hide:  hide one or more patterns in a bmp image
- Search: search and count each occurrence of a pattern in a bmp image

---
  <!-- _class: invert -->
# Our Options
<style>
    .container{
        display: flex;
    }
    .col{
        flex: 1;
    }
    .vertical-divider {
        border-right: 3px solid white;
    }
</style>

<div class="container">
<div class="col vertical-divider">
    <h4>hide</h4>

        --color
        --number
        --left
        --output
        --json
</div>

<div class="col">
    <h4>hide</h4>

    --color
    --fill
    --left
</div>

---
  <!-- _class: invert -->
# Improvement


- For two commands:
 -> give the choice of pattern to the user
 -> resolve the matching background unfindable effect
 
- For hide :
-> read a json with coordinates where to hide
 
- For search :
-> write a json with all coordinates of pattern found
->  give the choice of color to fill, or apply a mask


---
 <!-- _class: invert -->
# Sources
 - my PiccoCLI : https://picocli.info/
 - Peers
 - Microsoft Copilot: orthograph and commenting