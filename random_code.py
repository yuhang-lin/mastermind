# -*- coding: utf-8 -*-
"""
Generate four digit random code with each digit from 1 to 6

@author: Yuhang Lin
"""

import numpy as np
NUM_CODE = 10
NUM_COLOR = 4
for i in range(NUM_CODE):
    num_list = []
    for j in range(NUM_COLOR):
        num_list.append(np.random.randint(1, 7))
    print(num_list)
