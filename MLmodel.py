import numpy
import pandas
from sklearn import linear_model
# from sklearn.externals import joblib
import joblib

df = pandas.read_csv("linear_model_data.csv")

# get the columns
X = df[['CPU', 'Memory']]
y = df['Nodes']

regr = linear_model.LinearRegression()

# fill the regression object with data that describes the relationship
regr.fit(X, y)

# predict the action for CPU & Memory
pred_value1 = regr.predict([[10, 48]])
pred_value2 = regr.predict([[60, 48]])
pred_value3 = regr.predict([[90, 70]])

actions = {
           -2 : "Rounded Value : -2 \nAction : 2 nodes will be deactivated",
           -1 : "Rounded Value : -1 \nAction : 1 node will be deactivated",
            0 : "Rounded Value : 0. \nAction : Do nothing",
            1 : "Rounded Value : 1. \nAction : 1 node will be activated",
            2 : "Rounded Value : 2. \nAction : 2 nodes will be activated"
          }


print("Predicted value:", pred_value1)

# round up the predicted value
pred_value1 = int(numpy.round(pred_value1))

print(actions.get(pred_value1),'\n')


print("Predicted value:", pred_value2)

pred_value2 = int(numpy.round(pred_value2))

print(actions.get(pred_value2),'\n')


print("Predicted value:", pred_value3)

pred_value3 = int(numpy.round(pred_value3))

print(actions.get(pred_value3))

joblib.dump(regr, 'model.joblib')


