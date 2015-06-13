from django.contrib import admin

# Register your models here.

from .models import Tbcategories, TbcategoriesAdmin, \
					Tbgoods, TbgoodsAdmin, \
					Tborderitems, TborderitemsAdmin, \
					Tborders, TbordersAdmin, \
					Tbusers, TbusersAdmin

admin.site.register(Tbcategories, TbcategoriesAdmin)
admin.site.register(Tbgoods, TbgoodsAdmin)
admin.site.register(Tborderitems, TborderitemsAdmin)
admin.site.register(Tborders, TbordersAdmin)
admin.site.register(Tbusers, TbusersAdmin)
