function clearAllEvents() {
    localStorage.clear();
}

document.addEventListener('DOMContentLoaded', function () {
    const calendarEl = document.getElementById('calendar');
    const myModal = new bootstrap.Modal(document.getElementById('form'));
    const dangerAlert = document.getElementById('danger-alert');
    const close = document.querySelector('.btn-close');

    // TODO: to make an array from calendarEvents
    const myEvents = document.getElementById('myEvents').innerText;

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
        eventRender: function (info) { // TODO: to render properly user's events.
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

                // Edit context menu
                menu.querySelector('li:first-child').addEventListener('click', function () {
                    menu.remove();

                    const editModal = new bootstrap.Modal(document.getElementById('form'));
                    const modalTitle = document.getElementById('title');
                    const titleInput = document.getElementById('title');
                    const startDateInput = document.getElementById('startDate');
                    const endDateInput = document.getElementById('endDate');
                    const colorInput = document.getElementById('color');
                    const submitButton = document.getElementById('submit-button');
                    const cancelButton = document.getElementById('cancel-button');
                    modalTitle.innerHTML = 'Edit Event';
                    titleInput.value = info.event.title;
                    startDateInput.value = moment(info.event.startDate).format('YYYY-MM-DD');
                    endDateInput.value = moment(info.event.endDate, 'YYYY-MM-DD').subtract(1, 'day').format('YYYY-MM-DD');
                    colorInput.value = info.event.color.value;
                    submitButton.innerHTML = 'Save Changes';


                    editModal.show();

                    submitButton.classList.remove('btn-success')
                    submitButton.classList.add('btn-primary')

                    // Edit button
                    submitButton.addEventListener('click', function () {
                        const updatedEvents = {
                            id: info.event.id,
                            title: titleInput.value,
                            start: startDateInput.value,
                            end: moment(endDateInput.value, 'YYYY-MM-DD').add(1, 'day').format('YYYY-MM-DD'),
                            backgroundColor: colorInput.value
                        }

                        if (updatedEvents.end <= updatedEvents.start) { // add if statement to check end date
                            dangerAlert.style.display = 'block';
                            return;
                        }

                        const eventIndex = myEvents.findIndex(event => event.id === updatedEvents.id);
                        myEvents.splice(eventIndex, 1, updatedEvents);

                        localStorage.setItem('events', JSON.stringify(myEvents));

                        // Update the event in the calendar
                        const calendarEvent = calendar.getEventById(info.event.id);
                        calendarEvent.setProp('title', updatedEvents.title);
                        calendarEvent.setStart(updatedEvents.startDate);
                        calendarEvent.setEnd(updatedEvents.endDate);
                        calendarEvent.setProp('backgroundColor', updatedEvents.backgroundColor);


                        editModal.hide();

                    })
                });

                // Delete menu
                menu.querySelector('li:last-child').addEventListener('click', function () {
                    const deleteModal = new bootstrap.Modal(document.getElementById('delete-modal'));
                    const modalBody = document.getElementById('delete-modal-body');
                    const cancelModal = document.getElementById('cancel-button');
                    modalBody.innerHTML = `Are you sure you want to delete <b>"${info.event.title}"</b>`
                    deleteModal.show();

                    const deleteButton = document.getElementById('delete-button');
                    deleteButton.addEventListener('click', function () {
                        myEvents.splice(eventIndex, 1);
                        localStorage.setItem('events', JSON.stringify(myEvents));
                        calendar.getEventById(info.event.id).remove();
                        deleteModal.hide();
                        menu.remove();

                    });

                    cancelModal.addEventListener('click', function () {
                        deleteModal.hide();
                    })


                });
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

    myModal._element.addEventListener('hide.bs.modal', function () {
        dangerAlert.style.display = 'none';
        form.reset();
    });

});