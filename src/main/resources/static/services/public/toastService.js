import 'https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/8.0.0/mdb.umd.min.js';

const toastService = {
    success: (message) => {
        createToast(message, 'bg-success');
    },
    error: (message) => {
        createToast(message, 'bg-danger');
    }
};

function createToast(message, typeClass) {
    let toastContainer = document.getElementById('toastContainer');
    if (!toastContainer) {
        toastContainer = document.createElement('div');
        toastContainer.id = 'toastContainer';
        toastContainer.classList.add('toast-container', 'position-fixed', 'top-0', 'end-0', 'p-3');
        document.body.appendChild(toastContainer);
    }

    const toastElement = document.createElement('div');
    toastElement.classList.add('toast', 'align-items-center', typeClass, 'border-0', 'fade');
    toastElement.role = 'alert';
    toastElement.setAttribute('aria-live', 'assertive');
    toastElement.setAttribute('aria-atomic', 'true');

    toastElement.innerHTML = `
        <div class="d-flex">
            <div class="toast-body text-white">${message}</div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-mdb-dismiss="toast" aria-label="Close"></button>
        </div>
    `;

    toastContainer.appendChild(toastElement);

    const mdbToast = new mdb.Toast(toastElement);
    mdbToast.show();

    toastElement.addEventListener('hidden.mdb.toast', () => {
        toastElement.remove();
    });
}

export default toastService;
