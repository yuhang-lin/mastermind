#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
Plot several graphs using the stored by the game mastermind.

Author: Yuhang Lin
"""

import matplotlib.pyplot as plt
import os
import numpy as np

file_name = "user_stats.txt"
num_lost = 0
win_list = []
output_dir = './fig/'

if not os.path.exists(output_dir):
    os.makedirs(output_dir)

with open(file_name, "r") as fin:
    for line in fin:
        num = int(line.strip())
        if num == 0:
            num_lost += 1
        else:
            win_list.append(num)

# Plot pie chart to compare the percentage of winning and losing
data = [len(win_list), num_lost]
explode = (0, 0.1)
labels = ['Winning', 'Losing']
plt.pie(data, explode=explode, labels=labels, autopct='%1.1f%%',
        shadow=True, startangle=90)
plt.axis('equal')  # Equal aspect ratio ensures that pie is drawn as a circle.
plt.gcf().set_size_inches(9.0, 6.0)
plt.savefig(output_dir+'win_vs_lose.png', bbox_inches='tight', dpi=100)
plt.show()

# Plot time series of guesses to win
plt.ylabel("Number of guesses")
plt.xlabel("Index of game")
plt.plot(win_list, marker='o')
plt.gcf().set_size_inches(9.0, 6.0)
plt.savefig(output_dir+'win_vs_lose.png', bbox_inches='tight', dpi=100)
plt.show()

# Compare min, max, average number of guesses
max_guess = max(win_list)
min_guess = min(win_list)
mean = np.mean(win_list)
width = 0.35
bar_data = [max_guess, min_guess, mean]
ind = np.arange(len(bar_data))
fig, ax = plt.subplots()
bar = ax.bar(ind, bar_data, width)
plt.ylabel("Number of guesses")
ax.set_xticks(ind)
ax.set_xticklabels(('Maximum', 'Minimum', 'Average'))
plt.gcf().set_size_inches(9.0, 6.0)
plt.savefig(output_dir+'win_vs_lose.png', bbox_inches='tight', dpi=100)
plt.show()
