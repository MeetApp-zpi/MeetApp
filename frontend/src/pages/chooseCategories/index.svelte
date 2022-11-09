<script lang="ts">
    import Button from '../../lib/Button/Button.svelte';
    import execute from '../../lib/fetchWrapper';

    let categories = null;
    let userExistingInterests = null;

    $: console.log(categories);

    execute('categories', 'GET')
        .then((r) => r.json())
        .then((r) => (categories = r));

    execute('users/categories', 'GET')
        .then((r) => r.json())
        .then((r) => (userExistingInterests = r));
</script>

<div class="flex flex-col">
    <div class="">CZEŚĆ XYZ!</div>
    <div class="">
        <p>Wybranie zainteresowań pozwoli nam na dopasowanie treści specjalnie dla Ciebie.</p>
    </div>
    <div class="">bąbelki</div>
    <div class="">
        <Button clickHandler={() => null} class="" />
    </div>
    <div class="">Wybiorę zainteresowania następnym razem</div>
</div>
