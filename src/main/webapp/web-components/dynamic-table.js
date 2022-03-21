const template = document.createElement('template');
const tableData = [];
template.innerHTML = `
    <style>
        th {
            font-family: Annai MN;
            font-size: small;
            color: blue;
        }
        td {
            font-family: Annai MN;
            font-size: small;
        }
    </style>
    <table border='1' style='background-color: grey'></table>
`
export class DynamicTable extends HTMLElement {
    constructor() {
        super();
        this.attachShadow({mode : 'open'});
        this.shadowRoot.appendChild(template.content.cloneNode(true));
    }

    set data(json) {
        if (json) {
            this.setHeader(json.header);
            this.setBody(json.body, json.eventName);
        }
    }

    set update(json) {
        if (json) {
            const table = this.shadowRoot.querySelector("table");
            for(let x=1; x <= table.rows.length; x++) {
              table.deleteRow(x);
            }
            this.setBody(json.body, json.eventName);
        }
    }

    set add(json) {
        if (json) {
            this.setBody(json.body, json.eventName);
        }
    }

    get data() {
        return this.tableData;
    }

    //Table header
    setHeader(header) {
        if (header) {
            const tr = document.createElement('tr');
            header.forEach(x => {
                const th = document.createElement('th');
                th.innerText = x;
                tr.appendChild(th);
            });
            this.shadowRoot.querySelector("table").appendChild(tr);
        }
    }

    //Table body
    setBody(body, eventName) {
        if (body) {
            this.tableData = body;
            for (var x in body) {
                const tr = document.createElement('tr');
                tr.setAttribute('data', `${JSON.stringify(body[x])}`);
                tr.addEventListener('click', function() {
                    this.dispatchEvent(new CustomEvent(eventName, {
                        bubbles: true,
                        composed: true,
                        detail: tr.getAttribute('data')
                    }))
                });
                for (var y in body[x]) {
                    const td = document.createElement('td');
                    td.innerHTML = `${body[x][y]}`;
                    tr.appendChild(td);
                }
                this.shadowRoot.querySelector("table").appendChild(tr);
            }
        }
    }
}
window.customElements.define('dynamic-table', DynamicTable);