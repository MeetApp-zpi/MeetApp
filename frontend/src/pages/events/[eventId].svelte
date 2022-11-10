<script lang="ts">
    import Button from '../../lib/Button/Button.svelte';
    import Header from '../../lib/Header/Header.svelte';
    import execute from '../../lib/fetchWrapper';

    import FaCalendarAlt from 'svelte-icons/fa/FaCalendarAlt.svelte';
    import FaCalendarCheck from 'svelte-icons/fa/FaCalendarCheck.svelte';
    import FaMapMarkedAlt from 'svelte-icons/fa/FaMapMarkedAlt.svelte';
    import MdAccessTime from 'svelte-icons/md/MdAccessTime.svelte';
    import MdPeople from 'svelte-icons/md/MdPeople.svelte';

    export let eventId: number;

    let data = null;

    execute(`events/${eventId}`, 'GET')
        .then((res) => res.json())
        .then((r) => {
            data = r;
        });
</script>

<div class="h-screen">
    <Header />
    {#if data !== null}
        <div class="h-[calc(100%-4rem)] lg:h-[calc(100%-8rem)] overflow-auto">
            <div class="flex flex-col">
                <div class="relative m-4 self-stretch">
                    <img class="object-fill w-full" src="../no-image.png" alt="Event poster" />
                    <div class="absolute bottom-0 left-0 right-0 px-4 py-2 opacity-40 bg-ivory">
                        <h2 class="font-bold text-xl text-cocoa">
                            {data.title}
                        </h2>
                    </div>
                </div>
                <div class="text-pine bg-tea flex flex-col rounded-xl p-2 m-4">
                    <div class="flex flex-row">
                        <div class="w-6 mx-2">
                            <FaMapMarkedAlt />
                        </div>
                        {data.location.city.name}, {data.location.voivodeship.name}
                    </div>
                    <div class="flex flex-row">
                        <div class="w-6 mx-2">
                            <FaCalendarAlt />
                        </div>
                        {data.startDateTime.date}
                        <div class="w-6 mx-2">
                            <MdAccessTime />
                        </div>
                        {data.startDateTime.time}
                    </div>
                    <div class="flex flex-row">
                        <div class="w-6 mx-2">
                            <FaCalendarCheck />
                        </div>
                        {data.endDateTime.date}
                        <div class="w-6 mx-2">
                            <MdAccessTime />
                        </div>
                        {data.endDateTime.time}
                    </div>
                    <div class="flex flex-row">
                        <div class="w-6 mx-2">
                            <MdPeople />
                        </div>
                        <div class="text-orange">{data.enrolled} &nbsp;</div>
                        {data.personQuota !== null ? '/ ' + data.personQuota : ''}
                    </div>
                </div>
                <div class="m-4">
                    <h3 class="text-pine text-2xl">Opis</h3>
                    <p>{data.description}</p>
                </div>
                {#if data.schedule !== null}
                    <div class="m-4">
                        <h3 class="text-pine text-2xl">Harmonogram</h3>
                        <p>{data.schedule}</p>
                    </div>
                {/if}
            </div>
            <div class="py-8" />
        </div>
    {/if}
    <Button clickHandler={() => null} class="font-xl fixed w-64 bottom-4 left-0 right-0 mx-auto py-2 px-10">Zapisuję się!</Button>
</div>
