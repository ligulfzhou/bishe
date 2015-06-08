# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
#
# Also note: You'll have to insert the output of 'django-admin.py sqlcustom [app_label]'
# into your database.
from __future__ import unicode_literals

from django.db import models


class Tbcategories(models.Model):
    nid = models.IntegerField(primary_key=True)
    cname = models.TextField()

    class Meta:
        managed = False
        db_table = 'tbcategories'


class Tbgoods(models.Model):
    nid = models.IntegerField(primary_key=True)
    cname = models.TextField()
    dprice = models.FloatField()
    cdesc = models.TextField()
    ncategoryid = models.IntegerField()
    ncount = models.IntegerField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'tbgoods'


class Tborderitems(models.Model):
    nid = models.IntegerField(primary_key=True)
    ngood = models.ForeignKey(Tbgoods, blank=True, null=True)
    ncount = models.IntegerField()
    norder = models.ForeignKey('Tborders', blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'tborderitems'


class Tborders(models.Model):
    nid = models.IntegerField(primary_key=True)
    dcreate_at = models.DateTimeField(blank=True, null=True)
    nuser = models.ForeignKey('Tbusers', blank=True, null=True)
    dtotal = models.FloatField()
    nhandlered = models.IntegerField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'tborders'


class Tbusers(models.Model):
    nid = models.IntegerField(primary_key=True)
    cemail = models.TextField()
    cname = models.TextField()
    cpassword = models.TextField()
    dcreate_at = models.DateTimeField(blank=True, null=True)
    nrole = models.IntegerField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'tbusers'
