from django.contrib import admin

# Register your models here.

from .models import Tbcategories, Tbgoods, Tborderitems, Tborders, Tbusers

admin.site.register(Tbcategories)
admin.site.register(Tbgoods)
admin.site.register(Tborderitems)
admin.site.register(Tborders)
admin.site.register(Tbusers)
