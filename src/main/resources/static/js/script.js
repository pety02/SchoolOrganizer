document.addEventListener('DOMContentLoaded', function () {
    const calendarEl = document.getElementById('calendar');
    const myModal = new bootstrap.Modal(document.getElementById('form'));
    const dangerAlert = document.getElementById('danger-alert');
    const close = document.querySelector('.btn-close');

    const myEvents = JSON.parse(document.getElementById('myEvents').innerText);
    const calendar = new FullCalendar.Calendar(calendarEl, {
        customButtons: {
            addEventButton: {
                text: 'Add Event',
                click: function () {
                    myModal.show();
                    const modalTitle = document.getElementById('modal-title');
                    const submitButton = document.getElementById('submit-button');
                    modalTitle.innerHTML = 'Add Event';
                    submitButton.innerHTML = 'Add Event';
                    submitButton.classList.remove('btn-primary');
                    submitButton.classList.add('btn-success');


                    close.addEventListener('click', () => {
                        myModal.hide();
                    })
                }
            }
        },
        header: {
            center: 'addEventButton',
            right: 'today, prev,next '
        },
        plugins: ['dayGrid', 'interaction'],
        allDay: false,
        editable: false,
        selectable: false,
        unselectAuto: false,
        displayEventTime: false,
        events: myEvents
    });

    for (let i = 0; i < myEvents.length; i++) {
        const ev = {
            id: myEvents[i].calendarEventId,
            title: myEvents[i].title,
            start: myEvents[i].startDate,
            end: myEvents[i].endDate,
            allDay: false,
            backgroundColor: myEvents[i].color
        };
        calendar.addEvent(ev);
    }

    calendar.on('select', function (info) {

        const startDateInput = document.getElementById('startDate');
        const endDateInput = document.getElementById('endDate');
        startDateInput.value = info.startStr;
        const endDate = moment(info.endStr, 'YYYY-MM-DD').subtract(1, 'day').format('YYYY-MM-DD');
        endDateInput.value = endDate;
        if (startDateInput.value === endDate) {
            endDateInput.value = '';
        }
    });

    calendar.render();
    const form = document.querySelector('form');

    myModal._element.addEventListener('hide.bs.modal', function () {
        dangerAlert.style.display = 'none';
        form.reset();
    });
});

function showAddNewFileDialog() {
    const newFileForm = document.getElementById('addFileForm');
    newFileForm.style.display = "block";
}

function closeNewFileDialog() {
    const newFileForm = document.getElementById('addFileForm');
    newFileForm.style.display = "none";
}