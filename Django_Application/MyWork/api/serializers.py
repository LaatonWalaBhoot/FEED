from rest_framework import serializers
from api.models import Items

class ItemsSerializer(serializers.ModelSerializer):
    
    class Meta:
        
        model = Items
        fields = ('name', 'title', 'date', 'caption', 'description', 'url', 'choice')
        
