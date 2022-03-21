import {api, $} from './common.js';

api('http://localhost:8080/api/product').then(data => {
    const productTable = document.getElementById('products');
    const json = {
        header:  ['ID', 'Name'],
        body: data,
        eventName: 'productEvent'
    };
    productTable.data = json;
});

api('http://localhost:8080/api/stock').then(data => {
    const stockTable = document.getElementById('stocks');
    const json = {
        header: ['ID', 'Product', 'Quantity', 'Price', 'Expiry Date'],
        body: data,
        eventName: 'stockEvent'
    }
    stockTable.data = json;
});

document.addEventListener('productEvent', e => {
    const data = JSON.parse(e.detail);
    document.getElementById("product_id").innerText = data.id;
    document.getElementById("product_name").value = data.name;
});

document.addEventListener('stockEvent', e => {
    const data = JSON.parse(e.detail);
    document.getElementById("stock_id").innerText = data.id;
    document.getElementById("stock_product_name").value = data.productName;
    document.getElementById("stock_quantity").value = data.quantity;
    document.getElementById("stock_price").value = data.price;
    document.getElementById("stock_expiryDate").value = data.expiryDate;
});

const btnProd = document.getElementById('btn_prod');
btnProd.addEventListener('click', () => {
    if (btnProd.innerText == 'Show Products') {
        btnProd.innerText = 'Hide Products';
        document.getElementById('product').style.display = 'block';
    } else {
        btnProd.innerText = 'Show Products';
        document.getElementById('product').style.display = 'none';
    }
});

const btnStock = document.getElementById('btn_stock');
btnStock.addEventListener('click', () => {
    if (btnStock.innerText == 'Show Stocks') {
        btnStock.innerText = 'Hide Stocks';
        document.getElementById('stock').style.display = 'block';
    } else {
        btnStock.innerText = 'Show Stocks';
        document.getElementById('stock').style.display = 'none';
    }
});

const btnCust = document.getElementById('btn_cust');
btnCust.addEventListener('click', () => {
    if (btnCust.innerText == 'Show Customers') {
        btnCust.innerText = 'Hide Customers';
        document.getElementById('customer').style.display = 'block';
    } else {
        btnCust.innerText = 'Show Customers';
        document.getElementById('customer').style.display = 'none';
    }
});

$('productAdd').addEventListener('click', () => {
    if ($('product_name').value) {
        const request =  {name: $('product_name').value}
        api('http://localhost:8080/api/product', 'POST', request).then(response => {
            const data = {
                body: [response],
                eventName: 'productEvent'
            }
            $('products').add = data;
        });
    }
});

$('productSave').addEventListener('click', () => {
    if ($('product_name').value) {
        const request =  {id: $('product_id').innerText, name: $('product_name').value}
        api('http://localhost:8080/api/product', 'PUT', request).then(response => {
            const tableData = $('products').data;
            tableData.find(x => x.id === Number($('product_id').innerText)).name = $('product_name').value;
            const json = {
                body: tableData,
                eventName: 'productEvent'
            };
            $('products').update = json;
        });
    }
});

$('productDelete').addEventListener('click', () => {
    console.log($('products').data);
});