#!/usr/bin/env python2
#encoding: UTF-8

# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
import httplib
import json
import urllib2
#import tempfile

if __name__ == "__main__":
    app_id = "794472710688567"
    app_secret = "a496bf30e22ccb063efb5bf96b844281"
    client_credentials = str(app_id)+str(app_secret)
    url = "/v2.5/oauth/access_token?client_id="+app_id+"&amp;client_secret="+app_secret+"&amp;grant_type=client_credentials&redirect_uri=http://localhost:8080"
    conn1 = httplib.HTTPSConnection("graph.facebook.com")
    conn1.request("GET",str(url))
#    usertoken = "CAACEdEose0cBAM0hwzp9bLgWzW66uZBgpqUH9lQwBG2DR7obwCCixbtCUfewfqIS05URT3nBkORhoup6S6LUZAs8yXpnWfmpYPNtk9ICA4j9ijbokmVZAaIbarXsVZAJ6f8AWXCKgaOgHIX12ylS6ocUzAqWdad9J29lhOgc9ZAcZBYlsqTIZBqxFm50cS3stToeJMrwBCxySxHHSus0qnH"
    response = conn1.getresponse()
    print response.read()
#    usertoken = response.read()
    usertoken = "CAALSkZAe0TzcBAH9r6ysPQEpiSAq6SqoMljVEX5lWOtJVgAgzrFuKpQygM6LUa52yB09orq5b8nluc49tZArffrUWmsSkjGq2EbLbsHih25MH7omGane5OkZAPPkXAQYmvzSnDwovXqJDhAMKXquYVnjVe5I0TCw5Uc3MArOQreymQC5IiA"
    q = "chuva"
    url = "/search?q="+str(q)+"&type=user&access_token="+str(usertoken)  
#    usertoken=str(app_id)+"|"+str(app_secret)
#    url = "/endpoint?key="+str(q)+"&amp;access_token="+str(usertoken)
#    conn2 = httplib.HTTPSConnection("graph.facebook.com")
#    conn2.request("GET",str(url))
#    response = conn2.getresponse()
#    url = "/search?access_token="+str(usertoken)+"&q="+str(q)+"&type=post&limit=100&locale=pt-br"
    url = "https://graph.facebook.com"+url
    print url
    response = urllib2.urlopen(url).read()
    print response
    data = json.loads(response.decode('utf-8'))

    for name in data['data']:
        print (name['name'])
    
    for id in data['data']:
        print (id['id'])