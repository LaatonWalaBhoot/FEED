from django.shortcuts import render
from django.http import HttpResponse, JsonResponse
from django.views.decorators.csrf import csrf_exempt
from rest_framework.renderers import JSONRenderer
from rest_framework.parsers import JSONParser
from api.models import Items
from api.serializers import ItemsSerializer

# Create your views here.

@csrf_exempt
def items_list(request):
   
    if request.method == 'GET':
        items = Items.objects.all()
        serializer = ItemsSerializer(items, many=True)
        return JsonResponse(serializer.data, safe=False,json_dumps_params={'indent': 6})

    elif request.method == 'POST':
        data = JSONParser().parse(request)
        serializer = ItemsSerializer(data=data)
        if serializer.is_valid():
            serializer.save()
            return JsonResponse(serializer.data, status=201)
        return JsonResponse(serializer.errors, status=400)
