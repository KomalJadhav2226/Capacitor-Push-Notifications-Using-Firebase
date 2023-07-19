import flask
from flask import Flask, jsonify
import firebase_admin
from firebase_admin import credentials, messaging
import os,sys

cred = credentials.Certificate("ServiceAccountKey.json")

firebase_admin.initialize_app(cred)

app = Flask(__name__)
env = os.environ.get

# define a route to get all documents in a collection
@app.route('/api/send_notification/')
def send_notification():
  tokens = list()
  tokens.append("your FCM device token")
  message = messaging.MulticastMessage(
    notification=messaging.Notification(
      title="This is test message",
      body="Test Push Notifications"),
    data=None,
    tokens=tokens)

  response = messaging.send_multicast(message)

  return flask.jsonify(ok=True)

if __name__ == '__main__':
    app.run(debug=True)
