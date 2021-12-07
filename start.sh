#!/bin/bash

gzip -kdc /imdb_database.sql.gz | mysql -Dlibrary -hlocalhost -uroot -ppassword -v
