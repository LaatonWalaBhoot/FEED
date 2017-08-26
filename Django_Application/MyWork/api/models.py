# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey has `on_delete` set to the desired behavior.
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from __future__ import unicode_literals

from django.db import models


class Items(models.Model):
    name = models.CharField(max_length=25, blank=True, null=True)
    title = models.CharField(max_length=25, blank=True, null=True)
    date = models.CharField(max_length=25, blank=True, null=True)
    caption = models.CharField(max_length=100, blank=True, null=True)
    description = models.CharField(max_length=1000, blank=True, null=True)
    url = models.CharField(max_length=100, blank=True, null=True)
    choice = models.CharField(max_length=25, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'items'
