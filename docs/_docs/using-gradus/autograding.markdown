---
layout: post
title:  "Autograding"
parent: Using Gradus
search_exclude: false
---

# Autograding with Gradus

## Setting up your Gradus file

Create a new text file (`*.txt`) or Gradus file (`*.gradus`).

A sample file set up for autograding would look like this:

```
# Comments start with a pound sign 
# 
# Your subject goes on the left, followed by a '|' character and then the mark
# Decimals are accepted.

CS|80
Chemistry|94
History|23
English|40
Biology|99
Math|99
```

Note: There must **not** be a trailing newline character `\n`.

## Accessing the generated file

If you are hosting this yourself, then you can access the generated file, otherwise it will exist on a remote machine.

Paste the given file path into your favourite file explorer to access where it is. 