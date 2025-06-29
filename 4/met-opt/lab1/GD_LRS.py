#!/usr/bin/env python
# coding: utf-8

# # Gradient descent with learning rate scheduling

# ## Needed Libraries import

# In[43]:


from helpers import *
from scipy.optimize import OptimizeResult


# ## Gradient Descent implementations with different schedules

# ### General gradient descent implementation

# In[44]:


def gd_lrs(f, grad_f, x0, learning_rate_schedule, tol=1e-6, max_iter=1000):
    x = np.array(x0, dtype=float)

    nit = 0
    x_history = [x.copy()]

    for nit in range(max_iter):
        lr = learning_rate_schedule(nit)
        grad = grad_f(x)
        x -= lr * grad

        x_history.append(x.copy())

        if np.linalg.norm(grad) < tol:
            break

    return OptimizeResult(x=x,
                          fun=f(x),
                          nfev=0,
                          njev=nit + 1,
                          nit=nit + 1,
                          x_history=np.array(x_history))


# ### Const Decay

# In[45]:


def const_decay(decay_rate):
    return decay_rate


# ### Step Decay

# In[46]:


def step_decay(init, drop_rate, step_size, nit):
    return init * (drop_rate ** (nit // step_size))


# ### Exponential Decay

# In[47]:


def exponential_decay(init, decay_rate, nit):
    return init * np.exp(-decay_rate * nit)


# ### Cosine Annealing

# In[48]:


def cosine_annealing(init, nit, lr_max, eta_min=0):
    return eta_min + (init - eta_min) * (1 + np.cos(np.pi * nit / lr_max)) / 2


# ### List of modes for generalization

# In[49]:


decay_modes = [(lambda k: const_decay(0.1), "GD const decay"),
               (lambda k: step_decay(0.1, 0.5, 10, k), "GD step decay"),
               (lambda k: exponential_decay(0.1, 0.01, k), "GD exponential decay"),
               (lambda k: cosine_annealing(0.1, k, 100, eta_min=0.01), "GD cosine annealing")]


# ### Extraction for reuse

# In[50]:


# !jupyter nbconvert --to python GD_LRS.ipynb


# ## Results

# ### Symmetrical parabola: $(x - 3)^2 + (y + 2)^2$

# ### Step decay

# In[51]:


for decay in decay_modes:
    print_output([-5, 3],
                 lambda *args, **kwargs: gd_lrs(*args, learning_rate_schedule=decay[0], **kwargs),
                 func_sp,
                 grad_sp,
                 [3, -2],
                 decay[1])


# ### Rotated elliptical function: $2(x + 2)^2 + 4xy + 3(y - 4)^2$

# In[52]:


for decay in decay_modes:
    print_output([-3, -25],
                 lambda *args, **kwargs: gd_lrs(*args, learning_rate_schedule=decay[0], **kwargs),
                 func_re,
                 grad_re,
                 [-18, 16],
                 decay[1],
                 grid=[-30, 30])


# ### Elliptical function with scale: $8(x - 3)^2 + (y + 1)^2$

# ### Step decay

# In[53]:


for decay in decay_modes:
    print_output([-5, 3],
                 lambda *args, **kwargs: gd_lrs(*args, learning_rate_schedule=decay[0], **kwargs),
                 func_es,
                 grad_es,
                 [3, -1],
                 decay[1])

