<script>
    export let placeholder = 'Nazwa posta';
    let isValidated = false;

    export let postName = null;
    export let isValid = true;

    let errorMessage = null;

    export const getIsValid = () => {
        isValidated = true;
        return isValid;
    };

    export const getPostName = () => {
        return postName;
    };

    $: {
        if (errorMessage !== null) {
            if (postName === null || postName.length < 5) {
                isValid = false;

                if (isValidated) {
                    errorMessage.classList.remove('hidden');
                }
            } else {
                isValid = true;

                errorMessage.className += ' hidden';
            }
        }
    }
</script>

<div class="bg-ivory m-2 p-2">
    <input
        type="text"
        id="post-name"
        class="bg-ivory border-2 border-pickle text-gray
    px-2 py-3 text-2xl rounded-lg w-full block
    focus:outline-none focus:border-pickle focus:ring-1 focus:ring-pickle focus:text-cocoa"
        {placeholder}
        required
        bind:value={postName}
    />
    <p class="hidden peer-invalid:block text-red-500 text-sm mx-4" id="titleErrorMsg" bind:this={errorMessage}>Nazwa musi mieć minimum 5 znaków</p>
</div>
