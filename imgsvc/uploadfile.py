#!/usr/bin/env python
# -*- coding: utf-8 -*-

import httplib, urllib
import os.path
from os.path import join
import hashlib
from os import listdir
import sys

def uploadToURL(itempath):
    data 	= open(itempath, 'rb').read()

    imgid 	= hashlib.md5(data).hexdigest()
    sign 	= hashlib.md5(imgid + 'c6m9X28ghwQd').hexdigest()

    headers = {
        'Content-type':'application/octet−stream',
        'Accept':'text/plain'
    }
  
    conn = httplib.HTTPConnection("182.92.234.86:88")
    conn.request( "POST", "/uploadimage?md5=%s&sign=%s" % (imgid, sign), open(itempath), headers)

    response = conn.getresponse()
    print response.read()
    conn.close()
 
 
if __name__ == "__main__":
    if len(sys.argv)  != 2:
        print 'using python uploadfile.py [file fold path]'
        print 'such as python uploadfile.py /var/www/html/dqchat/Imageclass/files'
    else:
        filepath = sys.argv[1]
        for item in listdir(filepath):
            itempath = join(filepath, item)
            uploadToURL(itempath)
