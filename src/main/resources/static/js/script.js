function clearAllEvents() {
    localStorage.clear();
}

document.addEventListener('DOMContentLoaded', function () {
    const calendarEl = document.getElementById('calendar');
    const myModal = new bootstrap.Modal(document.getElementById('form'));
    const dangerAlert = document.getElementById('danger-alert');
    const close = document.querySelector('.btn-close');

    // TODO: to make an array from calendarEvents
    const myEvents = JSON.parse(document.getElementById('myEvents').innerText);

    const calendar = new FullCalendar.Calendar(calendarEl, {
        customButtons: {
            customButton: {
                text: 'Add Event',
                click: function () {
                    myModal.show();
                    const modalTitle = document.getElementById('modal-title');
                    const submitButton = document.getElementById('submit-button');
                    modalTitle.innerHTML = 'Add Event'
                    submitButton.innerHTML = 'Add Event'
                    submitButton.classList.remove('btn-primary');
                    submitButton.classList.add('btn-success');


                    close.addEventListener('click', () => {
                        myModal.hide()
                    })
                }
            }
        },
        header: {
            center: 'customButton', // add your custom button here
            right: 'today, prev,next '
        },
        plugins: ['dayGrid', 'interaction'],
        allDay: false,
        editable: true,
        selectable: true,
        unselectAuto: false,
        displayEventTime: false,
        events: myEvents,
        eventRender: function (info) {
            info.el.addEventListener('contextmenu', function (e) {
                e.preventDefault();
                let existingMenu = document.querySelector('.context-menu');
                existingMenu && existingMenu.remove();
                let menu = document.createElement('div');
                menu.className = 'context-menu';
                menu.innerHTML = `<ul>
            <li><i class="fas fa-edit"></i>Edit</li>
            <li><i class="fas fa-trash-alt"></i>Delete</li>
            </ul>`;
                const eventIndex = myEvents.findIndex(event => event.id === info.event.id);

                document.body.appendChild(menu);
                menu.style.top = e.pageY + 'px';
                menu.style.left = e.pageX + 'px';


                document.addEventListener('click', function () {
                    menu.remove();
                });
            });
        },

        eventDrop: function (info) {
            let myEvents = JSON.parse(localStorage.getItem('events')) || [];
            const eventIndex = myEvents.findIndex(event => event.id === info.event.id);
            const updatedEvent = {
                ...myEvents[eventIndex],
                id: info.event.id,
                title: info.event.title,
                start: moment(info.event.startDate).format('YYYY-MM-DD'),
                end: moment(info.event.endDate).format('YYYY-MM-DD'),
                backgroundColor: info.event.backgroundColor
            };
            myEvents.splice(eventIndex, 1, updatedEvent); // Replace old event data with updated event data
            localStorage.setItem('events', JSON.stringify(myEvents));
            console.log(updatedEvent);
        }

    });

    //document.getElementById('scheduler').addEventListener('onload', function () {

    for (let i = 0; i < myEvents.length; i++) {
        const ev = {
            id: myEvents[i].calendarEventId,
            title: myEvents[i].title,
            start: myEvents[i].startDate,
            end: myEvents[i].endDate,
            allDay: false,
            backgroundColor: myEvents[i].color
        };
        console.log(ev);

        calendar.addEvent(ev);
    }
    //})

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

    /*form.addEventListener('submit', function (event) {
        event.preventDefault(); // prevent default form submission

        // retrieve the form input values
        const title = document.querySelector('#title').value;
        const startDate = document.querySelector('#startDate').value;
        const endDate = document.querySelector('#endDate').value;
        const color = document.querySelector('#color').value;
        const endDateFormatted = moment(endDate, 'YYYY-MM-DD').add(1, 'day').format('YYYY-MM-DD');
        const eventId = uuidv4();

        console.log(eventId);

        if (endDateFormatted <= startDate) { // add if statement to check end date
            dangerAlert.style.display = 'block';
            return;
        }

        const newEvent = {
            id: eventId,
            title: title,
            start: startDate,
            end: endDateFormatted,
            allDay: false,
            backgroundColor: color
        };

        // render the new event on the calendar
        calendar.addEvent(newEvent);

        myModal.hide();
        form.reset();
    });*/

    myModal._element.addEventListener('hide.bs.modal', function () {
        dangerAlert.style.display = 'none';
        form.reset();
    });

});