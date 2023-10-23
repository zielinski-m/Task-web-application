/ Import the necessary modules.
import { $ } from 'jquery';

// Define the global variables.
const apiRoot = 'http://localhost:8080/v1/tasks';
const trelloApiRoot = 'http://localhost:8080/v1/trello';
const datatableRowTemplate = $('[data-datatable-row-template]').children()[0];
const $tasksContainer = $('[data-tasks-container]');

// Declare the global objects.
var availableBoards = {};
var availableTasks = {};

// Initialize the application.
getAllTasks();

// Function to get all available boards.
async function getAllAvailableBoards(callback, callbackArgs) {
    const requestUrl = trelloApiRoot + '/boards';

    const response = await fetch(requestUrl, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    });

    if (response.ok) {
        const boards = await response.json();
        callback(callbackArgs, boards);
    } else {
        console.error('Failed to get available boards:', response.statusText);
    }
}

// Function to create a new DOM element from the datatable row template.
function createElement(data) {
    const element = $(datatableRowTemplate).clone();

    element.attr('data-task-id', data.id);
    element.find('[data-task-name-section] [data-task-name-paragraph]').text(data.title);
    element.find('[data-task-name-section] [data-task-name-input]').val(data.title);

    element.find('[data-task-content-section] [data-task-content-paragraph]').text(data.content);
    element.find('[data-task-content-section] [data-task-content-input]').val(data.content);

    return element;
}

// Function to prepare select options for board or list.
function prepareBoardOrListSelectOptions(availableChoices) {
    return availableChoices.map((choice) => {
        return $('<option>')
            .addClass('crud-select__option')
            .val(choice.id)
            .text(choice.name || 'Unknown name');
    });
}

// Function to handle datatable rendering.
async function handleDatatableRender(taskData, boards) {
    // Clear the tasks container.
    $tasksContainer.empty();

    // Add the boards to the available boards object.
    boards.forEach((board) => {
        availableBoards[board.id] = board;
    });

    // Create a DOM element for each task.
    taskData.forEach((task) => {
        const $datatableRowEl = createElement(task);
        const $availableBoardsOptionElements = prepareBoardOrListSelectOptions(boards);

        $datatableRowEl.find('[data-board-name-select]').append($availableBoardsOptionElements);
        $datatableRowEl.appendTo($tasksContainer);
    });
}

// Function to get all tasks.
async function getAllTasks() {
    const requestUrl = apiRoot;

    const response = await fetch(requestUrl, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    });

    if (response.ok) {
        const tasks = await response.json();

        // Add the tasks to the available tasks object.
        tasks.forEach((task) => {
            availableTasks[task.id] = task;
        });

        // Get all available boards.
        getAllAvailableBoards(handleDatatableRender, tasks);
    } else {
        console.error('Failed to get tasks:', response.statusText);
    }
}

// Function to handle task update request.
async function handleTaskUpdateRequest() {
    // Get the parent element of the current element.
    const parentEl = $(this).parents('[data-task-id]');

    // Get the task ID from the parent element.
    const taskId = parentEl.attr('data-task-id');

    // Get the task title and content from the parent element.
    const taskTitle = parentEl.find('[data-task-name-input]').val();
    const taskContent = parentEl.find('[data-task-content-input]').val();

    // Construct the request URL.
    const requestUrl = apiRoot;
}