import math

import numpy as np
from sklearn.datasets import fetch_california_housing
from sklearn.preprocessing import StandardScaler


def load_california(scale=False, two_dim=True):
    data = fetch_california_housing(as_frame=False)
    x, y = data.data, data.target

    if scale:
        x = StandardScaler().fit_transform(x)

    if two_dim:
        return x, y

    return data


def california_objective(params, X, y):
    predictions = X @ params[:-1] + params[-1]
    mse = np.mean((predictions - y) ** 2)
    return mse


def rosenbrock(point):
    x, y = point
    return (1 - x) ** 2 + 100 * (y - x ** 2) ** 2


def himmelblau(point):
    x, y = point
    return (x ** 2 + y - 11) ** 2 + (x + y ** 2 - 7) ** 2


def bukin(point):
    x, y = point
    return 100 * np.sqrt(np.abs(y - 0.01 * x ** 2)) + 0.01 * np.abs(x + 10)


def rastrigin(point, a=1):
    x, y = point
    return (a * 2
            + (x ** 2 - a * math.cos(2 * math.pi * x))
            + (y ** 2 - a * math.cos(2 * math.pi * y)))
