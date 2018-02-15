#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
Compare the results of user and computer.

Author: Yuhang Lin
"""

import matplotlib.pyplot as plt
import os
import numpy as np

output_dir = './fig/'
if not os.path.exists(output_dir):
    os.makedirs(output_dir)

user_stats = "user_stats.txt"
num_lost_user = 0
num_won_user = 0
guess_user = []
with open(user_stats, "r") as fin:
    for line in fin:
        num = int(line.strip())
        if num == 0:
            num_lost_user += 1
            guess_user.append(12)
        else:
            num_won_user += 1
            guess_user.append(num)

computer_stats = "computer_stats.txt"
num_lost_computer = 0
num_won_computer = 0
guess_computer = []
with open(computer_stats, "r") as fin:
    for line in fin:
        num = int(line.strip())
        if num == 0:
            num_lost_computer += 1
            guess_computer.append(12)
        else:
            num_won_computer += 1
            guess_computer.append(num)

# Plot time series of guesses to win
plt.ylabel("Number of guesses")
plt.xlabel("Index of game")
plt.plot(guess_user, marker='o', label="User")
plt.plot(guess_computer, marker='x', label="Computer")
plt.gcf().set_size_inches(9.0, 6.0)
plt.savefig(output_dir+'num_guesses_time.png', bbox_inches='tight', dpi=100)
plt.legend(loc='best')
plt.show()

# Compare min, max, average number of guesses
width = 0.35
user_data = [max(guess_user), min(guess_user), np.mean(guess_user)]
computer_data = [max(guess_computer), min(guess_computer), np.mean(guess_computer)]
ind = np.arange(len(user_data))
fig, ax = plt.subplots()
bar_user = ax.bar(ind, user_data, width)
bar_computer = ax.bar(ind + width, computer_data, width)
plt.ylabel("Number of guesses")
ax.set_xticks(ind + width / 2)
ax.set_xticklabels(('Maximum', 'Minimum', 'Average'))
ax.legend((bar_user[0], bar_computer[0]), ('User', 'Computer'))
plt.gcf().set_size_inches(9.0, 6.0)
plt.savefig(output_dir+'max_min_avg.png', bbox_inches='tight', dpi=100)
plt.show()
