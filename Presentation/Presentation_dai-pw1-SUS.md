---
marp: true
theme: uncover
size: 16:9
paginate: true
author: M.Ischi and A.Leyre
title: DAI - Praticale Work 1
description: Praticale Work 1 for the DAI course at HEIG-VD, Switzerland
---
<!-- _class: invert -->
## DAI - Praticale Work 1
 
[Link to the reposoty](https://github.com/Ischi-Leyre/dai-pw1-SUS)
 
<small>M. Ischi and A.Leyre.</small>
 
---
<!-- _class: invert -->
# Objectives

- Make a programme using piccoCLI interface
- The programme must have two fonctionnalities
- Using the additionnal parameter and option

---
 <!-- _class: invert -->
# Our Programs

It can hide and search a specific pattern in a bmp file


![w:120 h:120](SUS_R.png) ![w:120 h:120](SUS_L.png)

---
  <!-- _class: invert -->
# Our Fonctionnalities

- Hide :  hide one or more pattern in a bmp image
- Search : search and count each occurrency of a pattern in a bmp image

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


- For two commands :
 -> give the choice of pattern to user
 -> resolve the matching background unfindable effect 
 
- For hide :
-> read a json with coordinates where to hide
 
- For search :
-> write a json with all cordinates of pattern found
->  give the choice of color to fill, or apply a mask


---
 <!-- _class: invert -->
# Sources
 - my PiccoCLI : https://picocli.info/
 - Peers
 - Microsoft Copilot : ortograph and commenting