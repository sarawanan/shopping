const template = document.createElement('template');
template.innerHTML = `
    <style>
        .customer-list {
            font-family: 'Arial', sans-serif;
            background: '#f4f4f4';
            width: 500x;
            display: grid;
            grid-template-columns: 1fr 2fr;
            grid-gap: 10px;
            marin-bottom: 15px;
            border-bottom: darkorchid 5px solid;
        }
        .customer-list img {
            width: 100%
        }
        .customer-list button {
            cursor: pointer;
            background: darkorchid;
            color: '#fff';
            border: 0;
            border-radius: 5px;
            padding: 5px 10px;
        }
    </style>
    <div class='customer-list'>
        <img />
        <div>
            <h3></h3>
            <div class="info">
                <p><slot name='phone'/></p>
                <p><slot name='email'/></p>
            </div>
            <button id="toggle-info">Hide Info</button>
        </div
    </div>
`;

export class CustomerList extends HTMLElement {
    constructor() {
        super();
        this.showInfo = true;
        this.attachShadow({mode: 'open'});
        this.shadowRoot.appendChild(template.content.cloneNode(true));
        this.$('h3').innerText = this.getAttribute('name');
        this.$('img').src = this.getAttribute('avatar');
    }

    toggleInfo() {
        this.showInfo = !this.showInfo;
        if (this.showInfo) {
            this.$('.info').style.display = 'block';
            this.$('#toggle-info').innerText = 'Hide Info';
        } else {
            this.$('.info').style.display = 'none';
            this.$('#toggle-info').innerText = 'Show Info';
        }
    }

    $(name) {
        return this.shadowRoot.querySelector(name);
    }

    connectedCallback() {
        this.$('#toggle-info').addEventListener('click', () => this.toggleInfo())
    }

    disconnectedCallback() {
        this.$('#toggle-info').removeEventListener();
    }
}
window.customElements.define('customer-list', CustomerList);