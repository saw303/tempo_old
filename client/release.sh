#!/bin/bash

ssh saw@zsc-supporter.ch 'rm -rf /usr/share/nginx/zsc-preview/*'
scp -r dist/* saw@zsc-supporter.ch:/usr/share/nginx/zsc-preview
